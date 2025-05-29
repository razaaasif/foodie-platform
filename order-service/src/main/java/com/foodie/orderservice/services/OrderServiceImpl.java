package com.foodie.orderservice.services;

import com.foodie.orderservice.constants.OrderStatus;
import com.foodie.orderservice.constants.PaymentStatus;
import com.foodie.orderservice.dto.*;
import com.foodie.orderservice.entity.Order;
import com.foodie.orderservice.entity.OrderItem;
import com.foodie.orderservice.exception.OrderException;
import com.foodie.orderservice.kafkaserivces.KafkaOrderProducer;
import com.foodie.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final KafkaOrderProducer kafkaOrderProducer;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest, Long userId) {
        // 1. Calculate total amount
        BigDecimal totalAmount = orderRequest.getItems().stream()
                .map(item -> item.getPricePerItem().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2. Create order object
        Order order = Order.builder()
                .userId(userId)
                .restaurantId(orderRequest.getRestaurantId())
                .status(OrderStatus.CREATED)
                .totalAmount(totalAmount)
                .orderTime(LocalDateTime.now())
                .paymentMethod(orderRequest.getPaymentMethod())
                .deliveryAddress(orderRequest.getDeliveryAddress())
                .paymentStatus(PaymentStatus.NOT_STARTED)
                .build();

        // 3. Create and set items before saving
        List<OrderItem> orderItems = orderRequest.getItems().stream()
                .map(itemDto -> OrderItem.builder()
                        .order(order)
                        .menuItemId(itemDto.getMenuItemId())
                        .quantity(itemDto.getQuantity())
                        .pricePerItem(itemDto.getPricePerItem())
                        .specialInstructions(itemDto.getSpecialInstructions())
                        .build())
                .collect(Collectors.toList());

        order.setItems(orderItems);

        try {
            // 4. Save order along with items (cascade should handle it)
            Order savedOrder = orderRepository.saveAndFlush(order);

            // 5. Send Kafka event (preferably async)
            kafkaOrderProducer.sendOrderCreatedEvent(mapToOrderCreated(savedOrder));

            return mapToOrderResponseDto(savedOrder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create order", e);
        }
    }

    private OrderCreatedEvent mapToOrderCreated(Order savedOrder) {
        return OrderCreatedEvent.builder()
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .totalAmount(savedOrder.getTotalAmount())
                .paymentMethod(savedOrder.getPaymentMethod())
                .items(savedOrder.getItems().stream()
                        .map(orderItem -> new OrderCreatedEvent.OrderItemEvent(
                                orderItem.getMenuItemId(),
                                orderItem.getQuantity(),
                                orderItem.getPricePerItem()
                        ))
                        .toList())
                .build();
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> OrderException.notFound(orderId));
        return mapToOrderResponseDto(order);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::mapToOrderResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateOrderStatus(OrderUpdateDTO orderDTO) {
        orderRepository.findById(orderDTO.getOrderId()).ifPresent(order -> {
            if (orderDTO.getOrderStatus() == OrderStatus.RIDER_ASSIGN) {
                order.setRiderId(orderDTO.getRiderId());
            } else if (orderDTO.getOrderStatus() == OrderStatus.DELIVERED) {
                order.setDeliveryTime(LocalDateTime.now());
            }
            order.setStatus(orderDTO.getOrderStatus());
            orderRepository.save(order);
            if (orderDTO.getOrderStatus() == OrderStatus.PREPARED) {
                this.kafkaOrderProducer.sendOrderPreparedEvent(new OrderPreparedEvent(order.getId(), order.getRestaurantId(), order.getStatus(), order.getDeliveryAddress()));
            }
            log.info("Order {} status updated to {}", order.getId(), orderDTO.getOrderStatus());
        });
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, Long userId) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> OrderException.notFound(orderId));

        if (!order.getUserId().equals(userId)) {
            throw OrderException.unauthorized("You are not authorized to cancel this order");
        }

        if (!order.getStatus().equals(OrderStatus.CREATED) &&
                !order.getStatus().equals(OrderStatus.PAYMENT_PENDING)) {
            throw OrderException.conflict("Order cannot be cancelled at this stage");
        }

        kafkaOrderProducer.sendOrderStatusUpdatedEvent(orderId, OrderStatus.CANCELLED.name());

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Transactional
    public void updateOrderPaymentStatus(PaymentStatusEventDTO paymentStatus) {
        try {
            Order order = orderRepository.findById(paymentStatus.getOrderId())
                    .orElseThrow(() -> {
                        log.warn("Order not found for ID: {}", paymentStatus.getOrderId());
                        return OrderException.notFound(paymentStatus.getOrderId());
                    });

            order.setPaymentStatus(paymentStatus.getPaymentStatus());

            switch (paymentStatus.getPaymentStatus()) {
                case SUCCESS:
                    order.setStatus(OrderStatus.PAYMENT_COMPLETED);
                    order.setTransactionId(paymentStatus.getTransactionId());
                    order.setPaymentTime(LocalDateTime.now());
                    orderRepository.save(order);

                    kafkaOrderProducer.sendOrderPaidEvent(
                            new OrderPaidEvent(order.getId(), order.getRestaurantId(), order.getUserId(), order.getTotalAmount())
                    );
                    log.info("Payment successful for orderId={}, transactionId={}", order.getId(), order.getTransactionId());
                    break;

                case INITIATED:
                    order.setStatus(OrderStatus.PAYMENT_PENDING);
                    orderRepository.save(order);
                    log.info("Payment initiated for orderId={}", order.getId());
                    break;

                case FAILED:
                    order.setStatus(OrderStatus.PAYMENT_FAILED_CANCELLED);
                    orderRepository.save(order);
                    log.info("Payment failed for orderId={}", order.getId());
                    break;

                default:
                    log.warn("Unhandled payment status: {}", paymentStatus.getPaymentStatus());
            }

        } catch (Exception ex) {
            log.error("Error updating payment status for orderId={}", paymentStatus.getOrderId(), ex);
            throw ex;
        }
    }


    @Override
    public String getAssignedRiderForOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> OrderException.notFound(orderId)).getRiderId();
    }

    private OrderResponseDTO mapToOrderResponseDto(Order order) {
        List<OrderItemDTO> itemDtos = order.getItems().stream()
                .map(this::mapToOrderItemDto)
                .collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .restaurantId(order.getRestaurantId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .orderTime(order.getOrderTime())
                .deliveryTime(order.getDeliveryTime())
                .deliveryAddress(order.getDeliveryAddress())
                .riderId(order.getRiderId())
                .transactionId(order.getTransactionId())
                .items(itemDtos)
                .paymentStatus(order.getPaymentStatus())
                .paymentTime(order.getPaymentTime() != null? order.getPaymentTime().toString():null)
                .delivered(order.getStatus() == OrderStatus.DELIVERED)
                .deliveredOn(order.getStatus() == OrderStatus.DELIVERED ? order.getUpdatedOn().toString() : null)
                .build();
    }

    private OrderItemDTO mapToOrderItemDto(OrderItem item) {
        return OrderItemDTO.builder()
                .menuItemId(item.getMenuItemId())
                .quantity(item.getQuantity())
                .pricePerItem(item.getPricePerItem())
                .specialInstructions(item.getSpecialInstructions())
                .build();
    }
}
package com.foodie.orderservice.services;

import com.foodie.orderservice.constants.OrderStatus;
import com.foodie.orderservice.constants.PaymentStatus;
import com.foodie.orderservice.dto.OrderItemDTO;
import com.foodie.orderservice.dto.OrderPaidEvent;
import com.foodie.orderservice.dto.OrderRequestDTO;
import com.foodie.orderservice.dto.OrderResponseDTO;
import com.foodie.orderservice.entity.Order;
import com.foodie.orderservice.entity.OrderItem;
import com.foodie.orderservice.exception.OrderException;
import com.foodie.orderservice.kafkaserivces.KafkaOrderProducer;
import com.foodie.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final KafkaOrderProducer kafkaOrderProducer;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest, Long userId) {
        // Calculate total amount
        BigDecimal totalAmount = orderRequest.getItems().stream()
                .map(item -> item.getPricePerItem().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        Order order = Order.builder()
                .userId(userId)
                .restaurantId(orderRequest.getRestaurantId())
                .status(OrderStatus.CREATED)
                .totalAmount(totalAmount)
                .orderTime(LocalDateTime.now())
                .deliveryAddress(orderRequest.getDeliveryAddress())
                .build();

        // Add order items
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
        Order savedOrder = orderRepository.save(order);

        // Send event to Kafka for payment processing
        kafkaOrderProducer.sendOrderCreatedEvent(savedOrder);

        return mapToOrderResponseDto(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId, Long userId) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> OrderException.notFound(orderId));

        if (!order.getUserId().equals(userId)) {
            throw OrderException.unauthorized("You are not authorized to view this order");
        }

        return mapToOrderResponseDto(order);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::mapToOrderResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus status, Long userId) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> OrderException.notFound(orderId));

        if (!order.getUserId().equals(userId)) {
            throw OrderException.unauthorized("You are not authorized to update this order");
        }

        order.setStatus(status);

        if (status == OrderStatus.DELIVERED) {
            order.setDeliveryTime(LocalDateTime.now());
        }

        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponseDto(updatedOrder);
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
    public void updateOrderPaymentStatus(Long orderId, PaymentStatus paymentStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> OrderException.notFound(orderId));

        if (PaymentStatus.SUCCESS == paymentStatus) {
            order.setStatus(OrderStatus.PAYMENT_COMPLETED);
            kafkaOrderProducer.sendOrderPaidEvent(new OrderPaidEvent(order.getId(), order.getRestaurantId(), order.getUserId(), order.getTotalAmount()));

        } else {
            order.setStatus(OrderStatus.PAYMENT_FAILED);
        }

        orderRepository.save(order);
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
                .paymentId(order.getPaymentId())
                .items(itemDtos)
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
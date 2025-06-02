package com.foodie.orderservice.services;

import com.foodie.commons.constants.OrderStatus;
import com.foodie.commons.dto.OrderPaidEvent;
import com.foodie.commons.dto.OrderPreparedEvent;
import com.foodie.commons.dto.PaymentStatusUpdateEventDTO;
import com.foodie.commons.utils.JsonUtils;
import com.foodie.orderservice.dto.*;
import com.foodie.orderservice.entity.Order;
import com.foodie.orderservice.entity.OrderItem;
import com.foodie.orderservice.entity.ProcessedEvent;
import com.foodie.orderservice.exception.OrderException;
import com.foodie.orderservice.exception.OrderNotFoundException;
import com.foodie.orderservice.kafkaserivces.KafkaOrderProducer;
import com.foodie.orderservice.mappers.OrderMapper;
import com.foodie.orderservice.repository.OrderRepository;
import com.foodie.orderservice.repository.ProcessedEventRepository;
import com.foodie.orderservice.util.TransactionCallbackUtils;
import com.foodie.orderservice.validators.OrderStatusTransitionValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final ProcessedEventRepository processedEventRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest, Long userId) {
        BigDecimal totalAmount = calculateTotalAmount(orderRequest);
        Order order = OrderMapper.createOrderFromOrderRequest(orderRequest, userId, totalAmount);
        List<OrderItem> orderItems = createOrderItem(orderRequest, order);
        order.setItems(orderItems);

        try {
            //save order and flush it for next event processing
            Order savedOrder = orderRepository.saveAndFlush(order);
            TransactionCallbackUtils.runAfterCommit(() -> kafkaOrderProducer.sendOrderCreatedEvent(OrderMapper.mapToOrderCreated(savedOrder)));
            OrderResponseDTO toSent = OrderMapper.mapToOrderResponseDto(savedOrder);
            this.sendDataToSocket(toSent);
            return toSent;
        } catch (Exception e) {
            log.error("Error creating order", e);
            throw new RuntimeException("Failed to create order", e);
        }
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) throws OrderException {
        Order order = fetchOrderOrThrow(orderId);
        return OrderMapper.mapToOrderResponseDto(order);
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderMapper::mapToOrderResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateOrderStatus(OrderUpdateDTO orderDTO) {
        log.debug("updateOrderStatus() started for {}", JsonUtils.toJson(orderDTO));

        boolean alreadyProcessed = processedEventRepository
                .existsByOrderIdAndEventType(orderDTO.getOrderId(), orderDTO.getOrderStatus());

        if (alreadyProcessed) {
            log.warn("updateOrderStatus() order already processed with Id: {}, event: {}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            return;
        }

        Order order = fetchOrderOrThrow(orderDTO.getOrderId());

        orderStateValidation(order,orderDTO);

        //apply concerns
        applyStatusSpecificChanges(order,orderDTO);
        // Save changes and event
        Order savedOrder = orderRepository.save(order);
        processedEventRepository.save(new ProcessedEvent(orderDTO.getOrderId(), orderDTO.getOrderStatus()));
        this.sendDataToSocket(OrderMapper.mapToOrderResponseDto(savedOrder));
        //apply event if required
        publishPreparedEventIfRequired(orderDTO, order);

        log.info("Order {} status updated to {}", order.getId(), orderDTO.getOrderStatus());
    }

    private static void orderStateValidation(Order order, OrderUpdateDTO orderDTO) {
        //checking for invalid state
        if (OrderStatus.isTerminatedState(order.getStatus())) {
            log.warn("updateOrderStatus() Order cannot be processed as it is in a terminal state: {}", JsonUtils.toJson(order));
            throw new IllegalArgumentException("Cannot update order in terminal state: " + order.getStatus());
        }
        // Check for invalid transitions
        OrderStatusTransitionValidator.validateTransition(order.getStatus(),orderDTO.getOrderStatus());
    }


    @Override
    @Transactional
    public void cancelOrder(Long orderId, Long userId) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> OrderException.notFound(orderId));

        if (!order.getUserId().equals(userId)) {
            throw OrderException.unauthorized("You are not authorized to cancel this order");
        }

        kafkaOrderProducer.sendOrderStatusUpdatedEvent(orderId, OrderStatus.CANCELLED.name());

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Transactional
    public void updateOrderPaymentStatus(PaymentStatusUpdateEventDTO paymentStatus) {
        try {
            Order order = orderRepository.findById(paymentStatus.getOrderId())
                    .orElseThrow(() -> {
                        log.warn("Order not found for ID: {}", paymentStatus.getOrderId());
                        return OrderException.notFound(paymentStatus.getOrderId());
                    });

            order.setPaymentStatus(paymentStatus.getPaymentStatus());
            this.handlePaymentEventAndSave(paymentStatus, order);
            this.sendDataToSocket(OrderMapper.mapToOrderResponseDto(order));
        } catch (Exception ex) {
            log.error("Error updating payment status for orderId={}", paymentStatus.getOrderId(), ex);
            throw ex;
        }
    }

    @Override
    public String getAssignedRiderForOrder(Long orderId) {
        String id = orderRepository.findById(orderId)
                .orElseThrow(() -> OrderException.notFound(orderId)).getRiderId();
        return  id;
    }


    private void publishPreparedEventIfRequired(OrderUpdateDTO orderDTO, Order order) {
        if (orderDTO.getOrderStatus() == OrderStatus.PREPARED) {
            TransactionCallbackUtils.runAfterCommit(() ->
                    kafkaOrderProducer.sendOrderPreparedEvent(
                            new OrderPreparedEvent(order.getId(), order.getRestaurantId(), order.getStatus(), order.getDeliveryAddress())
                    )
            );
        }
    }

    private void handlePaymentEventAndSave(PaymentStatusUpdateEventDTO paymentStatus, Order order) {
        switch (paymentStatus.getPaymentStatus()) {
            case INITIATED:
                order.setStatus(OrderStatus.PAYMENT_PENDING);
                orderRepository.save(order);
                log.info("Payment initiated for orderId={}", order.getId());
                break;
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
            case FAILED:
                order.setStatus(OrderStatus.PAYMENT_FAILED_CANCELLED);
                orderRepository.save(order);
                log.info("Payment failed for orderId={}", order.getId());
                break;
            default:
                log.warn("Unhandled payment status: {}", paymentStatus.getPaymentStatus());
        }
    }

    private void applyStatusSpecificChanges(Order order, OrderUpdateDTO orderDTO) {
        log.info("applyStatusSpecificChanges() starts");
        if (orderDTO.getOrderStatus() == OrderStatus.RIDER_ASSIGN) {
            order.setRiderId(orderDTO.getRiderId());
        } else if (orderDTO.getOrderStatus() == OrderStatus.DELIVERED) {
            order.setDeliveryTime(LocalDateTime.now());
        }
        order.setStatus(orderDTO.getOrderStatus());
        log.info("applyStatusSpecificChanges() ends");
    }

    private static List<OrderItem> createOrderItem(OrderRequestDTO orderRequest, Order order) {
        return orderRequest.getItems().stream()
                .map(itemDto -> OrderItem.builder()
                        .order(order)
                        .menuItemId(itemDto.getMenuItemId())
                        .quantity(itemDto.getQuantity())
                        .pricePerItem(itemDto.getPricePerItem())
                        .specialInstructions(itemDto.getSpecialInstructions())
                        .build())
                .collect(Collectors.toList());
    }

    private static BigDecimal calculateTotalAmount(OrderRequestDTO orderRequest) {
        return orderRequest.getItems().stream()
                .map(item -> item.getPricePerItem().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order fetchOrderOrThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }


    private void sendDataToSocket(OrderResponseDTO order) {
        messagingTemplate.convertAndSend("/topic/order-status", order);
    }

}
package com.foodie.orderservice.kafkaserivces;

import com.foodie.orderservice.constants.OrderStatus;
import com.foodie.orderservice.dto.OrderDeliveredEvent;
import com.foodie.orderservice.dto.OrderPreparedEvent;
import com.foodie.orderservice.dto.PaymentStatusEventDTO;
import com.foodie.orderservice.entity.Order;
import com.foodie.orderservice.repository.OrderRepository;
import com.foodie.orderservice.services.OrderService;
import com.foodie.orderservice.util.OrderHelperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created on 28/05/25.
 * Handles Kafka events related to Orders and updates order status accordingly.
 *
 * @author : aasif.raza
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaOrderConsumer {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "payment-status", groupId = "order-service-group")
    public void consumePaymentStatus(String message) {
        try {
            PaymentStatusEventDTO event = OrderHelperUtil.fromJson(message, PaymentStatusEventDTO.class);
            log.info("Received payment status: {}", event);
            orderService.updateOrderPaymentStatus(event.getOrderId(), event.getStatus());
        } catch (Exception e) {
            log.error("Error processing payment status event", e);
        }
    }

    @KafkaListener(topics = "order-preparing", groupId = "order-service-group")
    public void onOrderPreparing(OrderPreparedEvent event) {
        try {
            log.info("Received order-preparing event: {}", event.getOrderId());
            updateOrderStatus(event.getOrderId(), OrderStatus.PREPARING);
        } catch (Exception e) {
            log.error("Error processing order-preparing event for order {}", event.getOrderId(), e);
        }
    }

    @KafkaListener(topics = "order-prepared", groupId = "order-service-group")
    public void onOrderPrepared(OrderPreparedEvent event) {
        try {
            log.info("Received order-prepared event: {}", event.getOrderId());
            updateOrderStatus(event.getOrderId(), OrderStatus.PREPARED);
        } catch (Exception e) {
            log.error("Error processing order-prepared event for order {}", event.getOrderId(), e);
        }
    }

    @KafkaListener(topics = "order-out-for-delivery", groupId = "order-service-group")
    public void outForDelivery(OrderDeliveredEvent event) {
        try {
            log.info("outForDelivery event: {}", event.getOrderId());
            updateOrderStatus(event.getOrderId(), OrderStatus.OUT_FOR_DELIVERY);
        } catch (Exception e) {
            log.error("Error processing outForDelivery event for order {}", event.getOrderId(), e);
        }
    }

    @KafkaListener(topics = "order-delivered", groupId = "order-service-group")
    public void onOrderDelivered(OrderDeliveredEvent event) {
        try {
            log.info("Received delivery confirmation for order: {}", event.getOrderId());
            Optional<Order> optionalOrder = orderRepository.findById(event.getOrderId());

            if (optionalOrder.isEmpty()) {
                log.warn("Order not found: {}", event.getOrderId());
                return;
            }

            Order order = optionalOrder.get();

            if (order.getStatus() != OrderStatus.DELIVERED) {
                order.setStatus(OrderStatus.DELIVERED);
                orderRepository.save(order);
                log.info("Order marked as DELIVERED: {}", order.getId());

                // TODO: send notification to customer or any further processing
            } else {
                log.info("Order was already marked DELIVERED: {}", order.getId());
            }
        } catch (Exception e) {
            log.error("Error processing order-delivered event for order {}", event.getOrderId(), e);
        }
    }

    /**
     * Helper method to update the status of an order by ID.
     *
     * @param orderId the order ID
     * @param status  the new status to set
     */
    private void updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
            log.info("Order {} status updated to {}", order.getId(), status);
        });
    }
}

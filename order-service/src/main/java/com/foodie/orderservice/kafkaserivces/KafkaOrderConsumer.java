package com.foodie.orderservice.kafkaserivces;

import com.foodie.commons.constants.OrderStatus;
import com.foodie.commons.dto.OrderDeliveredEvent;
import com.foodie.commons.dto.OrderPreparedEvent;
import com.foodie.commons.dto.PaymentStatusUpdateEventDTO;
import com.foodie.commons.dto.RiderAssignedEvent;
import com.foodie.commons.utils.JsonUtils;
import com.foodie.orderservice.dto.*;
import com.foodie.orderservice.repository.OrderRepository;
import com.foodie.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

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

    @KafkaListener(topics = "payment-status-updated", groupId = "order-service-group")
    public void consumePaymentStatus(String message) {
        log.info("consumePaymentStatus() Received payment status: {}", message);
        try {
            PaymentStatusUpdateEventDTO event = JsonUtils.fromJson(message, PaymentStatusUpdateEventDTO.class);
            orderService.updateOrderPaymentStatus(event);
        } catch (Exception e) {
            log.error("Error processing payment status event", e);
        }
    }

    @KafkaListener(topics = "restaurant-confirm", groupId = "order-service-group")
    public void onRestaurantConfirm(String data) {
        OrderPreparedEvent event = JsonUtils.fromJson(data, OrderPreparedEvent.class);

        try {
            log.info("Received restaurant-confirm event: {}", event.getOrderId());
            OrderUpdateDTO order = new OrderUpdateDTO();
            order.setOrderId(event.getOrderId());
            order.setOrderStatus(event.getStatus());
            orderService.updateOrderStatus(order);
        } catch (Exception e) {
            log.error("Error processing restaurant-confirm event for order {}", event.getOrderId(), e);
        }
    }

    @KafkaListener(topics = "order-preparing", groupId = "order-service-group")
    public void onOrderPreparing(String data) {
        OrderPreparedEvent event = JsonUtils.fromJson(data, OrderPreparedEvent.class);

        try {
            log.info("Received order-preparing event: {} , {}", event.getOrderId(),event.getStatus());
            OrderUpdateDTO order = new OrderUpdateDTO();
            order.setOrderId(event.getOrderId());
            order.setOrderStatus(OrderStatus.PREPARING);
            orderService.updateOrderStatus(order);
        } catch (Exception e) {
            log.error("Error processing order-preparing event for order {}", event.getOrderId(), e);
        }
    }

    @KafkaListener(topics = "order-prepared", groupId = "order-service-group")
    public void onOrderPrepared(String data) {
        OrderPreparedEvent event = JsonUtils.fromJson(data, OrderPreparedEvent.class);

        try {
            log.info("Received order-prepared event: {} , {}", event.getOrderId(),event.getStatus());
            OrderUpdateDTO order = new OrderUpdateDTO();
            order.setOrderId(event.getOrderId());
            order.setOrderStatus(OrderStatus.PREPARED);
            orderService.updateOrderStatus(order);

        } catch (Exception e) {
            log.error("Error processing order-prepared event for order {}", event.getOrderId(), e);
        }
    }

    @KafkaListener(topics = "rider-assigned", groupId = "order-service-group")
    public void riderAssign(String data) {
        RiderAssignedEvent event = JsonUtils.fromJson(data, RiderAssignedEvent.class);

        try {
            log.info("Received rider-assign event: {} {} - {}", event.getOrderId(),OrderStatus.RIDER_ASSIGN, event.getRiderId());
            OrderUpdateDTO order = new OrderUpdateDTO();
            order.setOrderId(event.getOrderId());
            order.setRiderId(event.getRiderId());
            order.setOrderStatus(OrderStatus.RIDER_ASSIGN);
            this.orderService.updateOrderStatus(order);
        } catch (Exception e) {
            log.error("Error processing rider-assign event for order {}", event.getOrderId(), e);
        }
    }

    @KafkaListener(topics = "order-out-for-delivery", groupId = "order-service-group")
    public void outForDelivery(String data) {
        OrderDeliveredEvent event = JsonUtils.fromJson(data, OrderDeliveredEvent.class);

        try {
            log.info("outForDelivery event: {}  {}", event.getOrderId(),OrderStatus.PICKED_UP);
            OrderUpdateDTO order = new OrderUpdateDTO();
            order.setOrderId(event.getOrderId());
            order.setOrderStatus(OrderStatus.PICKED_UP);
            order.setRiderId(event.getRiderId());
            orderService.updateOrderStatus(order);
        } catch (Exception e) {
            log.error("Error processing outForDelivery event for order {}", event.getOrderId(), e);
        }
    }

    @KafkaListener(topics = "order-delivered", groupId = "order-service-group")
    public void onOrderDelivered(String data) {
        OrderDeliveredEvent event = JsonUtils.fromJson(data, OrderDeliveredEvent.class);
        try {
            log.info("Received delivery confirmation for order: {} {}", event.getOrderId(),OrderStatus.DELIVERED);
            OrderUpdateDTO order = new OrderUpdateDTO();
            order.setOrderId(event.getOrderId());
            order.setOrderStatus(OrderStatus.DELIVERED);
            orderService.updateOrderStatus(order);
        } catch (Exception e) {
            log.error("Error processing order-delivered event for order {}", event.getOrderId(), e);
        }
    }
}

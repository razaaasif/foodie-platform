package com.foodie.orderservice.kafkaserivces;

import com.foodie.orderservice.constants.OrderStatus;
import com.foodie.orderservice.constants.PaymentStatus;
import com.foodie.orderservice.dto.*;
import com.foodie.orderservice.entity.Order;
import com.foodie.orderservice.repository.OrderRepository;
import com.foodie.orderservice.services.OrderService;
import com.foodie.orderservice.util.OrderHelperUtil;
import com.foodie.orderservice.utils.JsonUtils;
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

    @KafkaListener(topics = "payment-status-updated", groupId = "order-service-group")
    public void consumePaymentStatus(String message) {
        log.info("consumePaymentStatus() Received payment status: {}", message);
        try {
            PaymentStatusEventDTO event = JsonUtils.fromJson(message, PaymentStatusEventDTO.class);
            orderService.updateOrderPaymentStatus(event);
        } catch (Exception e) {
            log.error("Error processing payment status event", e);
        }
    }

    @KafkaListener(topics = "order-preparing", groupId = "order-service-group")
    public void onOrderPreparing(String data) {
        OrderPreparedEvent event = JsonUtils.fromJson(data, OrderPreparedEvent.class);

        try {
            log.info("Received order-preparing event: {}", event.getOrderId());
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
            log.info("Received order-prepared event: {}", event.getOrderId());
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
        log.info("Received rider-assign event: {}", data);
        RiderAssignedEvent event = JsonUtils.fromJson(data, RiderAssignedEvent.class);
        try {
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
            log.info("outForDelivery event: {}", event.getOrderId());
            OrderUpdateDTO order = new OrderUpdateDTO();
            order.setOrderId(event.getOrderId());
            order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
            orderService.updateOrderStatus(order);
        } catch (Exception e) {
            log.error("Error processing outForDelivery event for order {}", event.getOrderId(), e);
        }
    }

    @KafkaListener(topics = "order-delivered", groupId = "order-service-group")
    public void onOrderDelivered(String data) {
        OrderDeliveredEvent event = JsonUtils.fromJson(data, OrderDeliveredEvent.class);
        try {
            log.info("Received delivery confirmation for order: {}", event.getOrderId());
            OrderUpdateDTO order = new OrderUpdateDTO();
            order.setOrderId(event.getOrderId());
            order.setOrderStatus(OrderStatus.DELIVERED);
            orderService.updateOrderStatus(order);
        } catch (Exception e) {
            log.error("Error processing order-delivered event for order {}", event.getOrderId(), e);
        }
    }
}

package com.foodie.orderservice.kafkaserivces;

import com.foodie.orderservice.dto.OrderPaidEvent;
import com.foodie.orderservice.dto.PaymentRequestDTO;
import com.foodie.orderservice.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Service
@RequiredArgsConstructor
public class KafkaOrderProducer {
    @Value("${kafka.topic.order-created}")
    private String orderCreatedTopicName;

    @Value("${kafka.topic.payment-processed}")
    private String paymentProcessedTopicName;

    @Value("${kafka.topic.order-status-updated}")
    private String orderStatusUpdatedTopicName;

    @Value("${kafka.topic.order-prepare}")
    private String orderPrepareTopicName;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderCreatedEvent(Order order) {
        PaymentRequestDTO paymentRequest = PaymentRequestDTO.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .amount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod()) // Default
                .build();

        kafkaTemplate.send(orderCreatedTopicName, paymentRequest);
    }

    public void sendOrderStatusUpdatedEvent(Long orderId, String status) {
        kafkaTemplate.send(orderStatusUpdatedTopicName, String.valueOf(orderId), status);
    }

    public void sendOrderPaidEvent(OrderPaidEvent orderPaidEvent) {
        kafkaTemplate.send(orderPrepareTopicName, orderPaidEvent);
    }
}
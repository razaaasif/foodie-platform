package com.foodie.orderservice.kafkaserivces;

import com.foodie.commons.dto.OrderCreatedEvent;
import com.foodie.commons.dto.OrderPaidEvent;
import com.foodie.commons.dto.OrderPreparedEvent;
import com.foodie.commons.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaOrderProducer {
    @Value("${kafka.topic.order-created}")
    private String orderCreatedTopicName;

    @Value("${kafka.topic.payment-processed}")
    private String paymentProcessedTopicName;

    @Value("${kafka.topic.order-status-updated}")
    private String orderStatusUpdatedTopicName;

    @Value("${kafka.topic.order-prepare}")
    private String orderPrepareTopicName;

    @Value("${kafka.topic.order-assign}")
    private String orderAssignTopicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Async("kafkaExecutor")
    public void sendOrderCreatedEvent(OrderCreatedEvent order) {
        try {
            log.info("sendOrderCreatedEvent() Thread : {}" ,Thread.currentThread().getName());
            kafkaTemplate.send(orderCreatedTopicName, JsonUtils.toJson(order));
        } catch (Exception ex) {
            log.warn("Kafka event sending failed", ex);
        }
    }

    @Async("kafkaExecutor")
    public void sendOrderPreparedEvent(OrderPreparedEvent order) {
        kafkaTemplate.send(orderAssignTopicName, JsonUtils.toJson(order));
    }

    @Async("kafkaExecutor")
    public void sendOrderStatusUpdatedEvent(Long orderId, String status) {
        kafkaTemplate.send(orderStatusUpdatedTopicName, String.valueOf(orderId), status);
    }

    @Async("kafkaExecutor")
    public void sendOrderPaidEvent(OrderPaidEvent orderPaidEvent) {
        kafkaTemplate.send(orderPrepareTopicName, JsonUtils.toJson(orderPaidEvent));
    }
}
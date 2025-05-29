package com.foodie.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Configuration
public class KafkaConfig {
    @Value("${kafka.topic.order-created}")
    private String orderCreatedTopicName;

    @Value("${kafka.topic.payment-processed}")
    private String paymentProcessedTopicName;

    @Value("${kafka.topic.order-status-updated}")
    private String orderStatusUpdatedTopicName;

    @Value("${kafka.topic.order-prepare}")
    private String orderPrepareTopicName;

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name(orderCreatedTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentProcessedTopic() {
        return TopicBuilder.name(paymentProcessedTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderStatusUpdatedTopic() {
        return TopicBuilder.name(orderPrepareTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderPrepareTopic() {
        return TopicBuilder.name("order-prepare")
                .partitions(3)
                .replicas(1)
                .build();
    }
}

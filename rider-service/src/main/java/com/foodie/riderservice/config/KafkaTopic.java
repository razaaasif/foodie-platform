package com.foodie.riderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
public class KafkaTopic {
    @Bean
    public NewTopic riderAssignedTopic() {
        return TopicBuilder.name("rider-assigned")
                .partitions(3)
                .replicas(1)
                .build();
    }


    @Bean
    public NewTopic orderOutForDelivery() {
        return TopicBuilder.name("order-out-for-delivery")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic orderDeliveredTopic() {
        return TopicBuilder.name("order-delivered")
                .partitions(3)
                .replicas(1)
                .build();
    }

}

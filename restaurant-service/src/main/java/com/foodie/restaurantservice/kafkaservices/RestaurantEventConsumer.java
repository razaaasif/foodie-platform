package com.foodie.restaurantservice.kafkaservices;

import com.foodie.restaurantservice.dto.OrderPaidEvent;
import com.foodie.restaurantservice.repository.OrderRepository;
import com.foodie.restaurantservice.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantEventConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "order-prepare", groupId = "restaurant-service-group")
    public void onOrderPaid(String data) {
        OrderPaidEvent event = JsonUtils.fromJson(data, OrderPaidEvent.class);
        log.info("Received order to prepare: {}", data);
    }
}

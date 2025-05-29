package com.foodie.restaurantservice.kafkaservices;

import com.foodie.restaurantservice.constants.OrderStatus;
import com.foodie.restaurantservice.dto.OrderPaidEvent;
import com.foodie.restaurantservice.dto.OrderPreparedEvent;
import com.foodie.restaurantservice.entity.Order;
import com.foodie.restaurantservice.repository.OrderRepository;
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
    public void onOrderPaid(OrderPaidEvent event) {
        log.debug("Received order to prepare: {}", event.orderId());

        OrderPreparedEvent preparedEvent = new OrderPreparedEvent(event.orderId(), event.restaurantId(), OrderStatus.PREPARING);

        kafkaTemplate.send("order-preparing", preparedEvent);
        log.debug("Order preparing for  and event sent: {}", event);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        preparedEvent = new OrderPreparedEvent(event.orderId(), event.restaurantId(), OrderStatus.PREPARED);
        kafkaTemplate.send("order-prepared", preparedEvent);
        log.debug("Order prepared {}", event);

    }
}

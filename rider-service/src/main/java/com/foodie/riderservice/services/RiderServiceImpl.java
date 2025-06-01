package com.foodie.riderservice.services;


import com.foodie.commons.dto.OrderDeliveredEvent;
import com.foodie.commons.dto.OrderPreparedEvent;
import com.foodie.commons.dto.RiderAssignedEvent;
import com.foodie.commons.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Service
@Slf4j
@AllArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Override
    public void assignRider(OrderPreparedEvent event) {
        String riderId = UUID.randomUUID().toString(); // mock assignment
        String eta = "15 minutes";
        String riderAssignedEvent = JsonUtils.toJson(new RiderAssignedEvent(
                event.getOrderId(), riderId, eta,event.getDeliveryAddress()
        ));

        log.info("Rider assigned: {}", riderAssignedEvent);
        try {
            kafkaTemplate.send("rider-assigned", riderAssignedEvent);
        } catch (Exception e) {
            log.error("assignRider Error while assigning rider");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void takeOrder(Long orderId, String riderId) {
        String deliveredEvent = JsonUtils.toJson(new OrderDeliveredEvent(orderId, riderId, Instant.now().toString()));
        log.info("Order order-out-for-delivery: {}", deliveredEvent);
        kafkaTemplate.send("order-out-for-delivery", deliveredEvent);
    }

    @Override
    public void deliverOrder(Long orderId, String riderId) {
        String deliveredEvent = JsonUtils.toJson(new OrderDeliveredEvent(orderId, riderId, Instant.now().toString()));
        log.info("Order delivered: {}", deliveredEvent);
        kafkaTemplate.send("order-delivered", deliveredEvent);
    }

}
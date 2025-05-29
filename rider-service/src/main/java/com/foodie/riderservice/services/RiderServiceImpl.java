package com.foodie.riderservice.services;

import com.foodie.riderservice.dto.OrderDeliveredEvent;
import com.foodie.riderservice.dto.OrderPreparedEvent;
import com.foodie.riderservice.dto.RiderAssignedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void assignRider(OrderPreparedEvent event) {
        String riderId = UUID.randomUUID().toString(); // mock assignment
        String eta = "15 minutes";

        RiderAssignedEvent riderAssignedEvent = new RiderAssignedEvent(
                event.getOrderId(), riderId, eta
        );

        log.info("Rider assigned: {}", riderAssignedEvent);
        kafkaTemplate.send("rider-assigned", riderAssignedEvent);
    }

    @Override
    public void deliverOrder(String orderId, String riderId, String deliveryTime) {
        OrderDeliveredEvent deliveredEvent = new OrderDeliveredEvent(orderId, riderId, deliveryTime);
        log.info("Order delivered: {}", deliveredEvent);
        kafkaTemplate.send("order-delivered", deliveredEvent);
    }

    @Override
    public void takeOrder(String orderId, String riderId, String deliveryTime) {
        OrderDeliveredEvent deliveredEvent = new OrderDeliveredEvent(orderId, riderId, deliveryTime);
        log.info("Order order-out-for-delivery: {}", deliveredEvent);
        kafkaTemplate.send("order-out-for-delivery", deliveredEvent);
    }
}
package com.foodie.riderservice.kafkaservice;

import com.foodie.riderservice.dto.OrderPreparedEvent;
import com.foodie.riderservice.services.RiderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RiderEventConsumer {

    private final RiderService riderService;

    @KafkaListener(topics = "order-prepared", groupId = "rider-service-group")
    public void onOrderPrepared(OrderPreparedEvent event) {
        log.info("Received OrderPreparedEvent: {}", event.getOrderId());
        riderService.assignRider(event);
    }
}
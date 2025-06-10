package com.foodie.ridermatcherservice.kafka;


import com.foodie.commons.dto.RiderMatchRequestEvent;
import com.foodie.commons.utils.JsonUtils;
import com.foodie.ridermatcherservice.services.RiderMatcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Created on 07/06/25.
 *
 * @author : aasif.raza
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantEventConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RiderMatcherService riderMatcherService;
    @KafkaListener(topics = "rider-match-request", groupId = "rider-matched-service-group")
    public void riderMatchRequestEvent(String message) {
        log.info("riderMatchRequestEvent() Received payment status: {}", message);
        try {
            RiderMatchRequestEvent event = JsonUtils.fromJson(message, RiderMatchRequestEvent.class);
            riderMatcherService.assignNearestRider(event);
        } catch (Exception e) {
            log.error("riderMatchRequestEvent() Error processing payment status event", e);
        }
    }
}

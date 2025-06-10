package com.foodie.ridermatcherservice.services;

import com.foodie.commons.constants.RiderStatus;
import com.foodie.commons.dto.RiderMatchRequestEvent;
import com.foodie.commons.utils.CommonUtils;
import com.foodie.ridermatcherservice.entity.Rider;
import com.foodie.ridermatcherservice.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 07/06/25.
 *
 * @author : aasif.raza
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RiderMatcherServiceImpl implements RiderMatcherService {
    private final RiderRepository riderRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void assignNearestRider(RiderMatchRequestEvent riderMatchRequestEvent) {
        List<Rider> riders = this.riderRepository.findByPinCodeAndRiderStatus(riderMatchRequestEvent.getPinCode(), RiderStatus.AVAILABLE);
        if (CommonUtils.isNullOrEmpty(riders)) {
            log.info("assignNearestRider() no nears riders found for order : {}", riderMatchRequestEvent.getOrderId());
            return;
        }

        //send notification request
        riders.forEach(rider -> {
            this.messagingTemplate.convertAndSend("/topic/" + rider.getId(), riderMatchRequestEvent);
        });
    }
}

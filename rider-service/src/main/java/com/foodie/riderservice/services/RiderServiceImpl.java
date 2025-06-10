package com.foodie.riderservice.services;


import com.foodie.commons.constants.RiderStatus;
import com.foodie.commons.dto.OrderDeliveredEvent;
import com.foodie.commons.dto.OrderPreparedEvent;
import com.foodie.commons.dto.RiderAssignedEvent;
import com.foodie.commons.exception.EntityAlreadyExistsException;
import com.foodie.commons.utils.JsonUtils;
import com.foodie.riderservice.dto.RiderDetailDTO;
import com.foodie.riderservice.entity.Rider;
import com.foodie.riderservice.repository.RiderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final RiderRepository riderRepository;

    @Override
    public void assignRider(OrderPreparedEvent event) {
        String riderId = UUID.randomUUID().toString(); // mock assignment
        String eta = "15 minutes";
        String riderAssignedEvent = JsonUtils.toJson(new RiderAssignedEvent(
                event.getOrderId(), riderId, eta, event.getDeliveryAddress()
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
    @Transactional
    public void takeOrder(Long orderId, String riderId) {
        Rider rider = this.riderRepository.findById(riderId).orElseThrow(() -> new EntityNotFoundException("Rider not found with : " + riderId));
        rider.setRiderStatus(RiderStatus.BUSY);
        rider.setUpdatedOn(Instant.now());
        this.riderRepository.save(rider);
        String deliveredEvent = JsonUtils.toJson(new OrderDeliveredEvent(orderId, riderId, Instant.now().toString()));
        log.info("Order order-out-for-delivery: {}", deliveredEvent);
        kafkaTemplate.send("order-out-for-delivery", deliveredEvent);
    }

    @Override
    @Transactional
    public RiderDetailDTO createRider(RiderDetailDTO riderDetailDTO) {
        log.info("createRider() started");
        String phone = riderDetailDTO.getPhone();
        if (this.riderRepository.findByPhone(phone).isPresent()) {
            throw new EntityAlreadyExistsException("Rider", "phone", riderDetailDTO.getPhone());
        }
        Rider rider = this.riderRepository.save(riderDetailDTOToRider(riderDetailDTO));
        log.info("createRider() started finishes ID : {}", rider.getId());
        return riderToRiderDetailDTO(rider);

    }

    @Override
    @Transactional
    public void deliverOrder(Long orderId, String riderId) {
        Rider rider = this.riderRepository.findById(riderId).orElseThrow(() -> new EntityNotFoundException("Rider not found with : " + riderId));
        rider.setRiderStatus(RiderStatus.AVAILABLE);
        rider.setUpdatedOn(Instant.now());
        this.riderRepository.save(rider);
        String deliveredEvent = JsonUtils.toJson(new OrderDeliveredEvent(orderId, riderId, Instant.now().toString()));
        log.info("Order delivered: {}", deliveredEvent);
        kafkaTemplate.send("order-delivered", deliveredEvent);
    }

    private Rider riderDetailDTOToRider(RiderDetailDTO dto) {
        Rider rider = new Rider();
        rider.setName(dto.getName());
        rider.setPhone(dto.getPhone());
        rider.setPinCode(dto.getPinCode());
        rider.setRiderStatus(dto.getRiderStatus());
        rider.setCreatedOn(Instant.now());
        rider.setUpdatedOn(Instant.now());
        return rider;
    }

    private RiderDetailDTO riderToRiderDetailDTO(Rider rider) {
        return RiderDetailDTO.builder()
                .id(rider.getId())
                .name(rider.getName())
                .phone(rider.getPhone())
                .pinCode(rider.getPinCode())
                .riderStatus(rider.getRiderStatus())
                .build();
    }
}
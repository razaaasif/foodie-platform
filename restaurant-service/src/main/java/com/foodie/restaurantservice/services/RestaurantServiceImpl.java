package com.foodie.restaurantservice.services;

import com.foodie.commons.constants.OrderStatus;
import com.foodie.commons.constants.RestaurantStatus;
import com.foodie.commons.dto.KeyValueDTO;
import com.foodie.commons.dto.OrderStateChangeEvent;
import com.foodie.restaurantservice.dto.RestaurantDTO;
import com.foodie.restaurantservice.entity.Restaurant;
import com.foodie.restaurantservice.repository.RestaurantRepository;
import com.foodie.restaurantservice.util.JsonUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    @Override
    public RestaurantDTO save(RestaurantDTO dto) {
        return mapToDTO(restaurantRepository.save(mapToEntity(dto)));
    }

    @Override
    public List<RestaurantDTO> findAll() {
        return restaurantRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO findById(Long id) {
        Restaurant r = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        return mapToDTO(r);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public void markOrderPreparing(OrderStateChangeEvent orderStateChange) {
        orderStateChange.setStatus(OrderStatus.PREPARING);
        kafkaTemplate.send("order-preparing", JsonUtils.toJson(orderStateChange));
    }

    @Override
    public void markOrderPrepared(OrderStateChangeEvent orderStateChange) {
        orderStateChange.setStatus(OrderStatus.PREPARED);
        kafkaTemplate.send("order-prepared", JsonUtils.toJson(orderStateChange));
    }

    @Override
    public void markOrderConfirm(OrderStateChangeEvent orderStateChange) {
        orderStateChange.setStatus(OrderStatus.RESTAURANT_CONFIRMED);
        kafkaTemplate.send("restaurant-confirm", JsonUtils.toJson(orderStateChange));
    }

    @Override
    @Transactional
    public String changeStatus(KeyValueDTO<Long, RestaurantStatus> keyValueDTO) {
        Restaurant restaurant = getRestaurant(keyValueDTO.getKey());
        restaurant.setStatus(keyValueDTO.getValue());
        this.restaurantRepository.save(restaurant);
        return keyValueDTO.getValue().getDescription();
    }

    @Override
    public String isRestaurantOpen(Long restaurantId) {
        return this.getRestaurant(restaurantId).getStatus().getDescription();
    }


    private Restaurant getRestaurant(Long id) {
        return this.restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Requested restaurant does not exits" + id));
    }

    private RestaurantDTO mapToDTO(Restaurant r) {
        return RestaurantDTO.builder()
                .id(r.getId())
                .name(r.getName())
                .address(r.getAddress())
                .phone(r.getPhone())
                .zipCode(r.getZipCode())
                .build();
    }


    private Restaurant mapToEntity(RestaurantDTO dto) {
        return Restaurant.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .zipCode(dto.getZipCode())
                .build();
    }
}
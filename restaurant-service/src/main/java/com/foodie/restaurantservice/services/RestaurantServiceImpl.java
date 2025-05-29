package com.foodie.restaurantservice.services;

import com.foodie.restaurantservice.constants.OrderStatus;
import com.foodie.restaurantservice.dto.OrderPreparedEvent;
import com.foodie.restaurantservice.dto.RestaurantDTO;
import com.foodie.restaurantservice.entity.Restaurant;
import com.foodie.restaurantservice.repository.RestaurantRepository;
import com.foodie.restaurantservice.util.JsonUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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

    private final RestaurantRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public RestaurantDTO save(RestaurantDTO dto) {
        return mapToDTO(repository.save(mapToEntity(dto)));
    }

    @Override
    public List<RestaurantDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO findById(Long id) {
        Restaurant r = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        return mapToDTO(r);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void markOrderPreparing( OrderPreparedEvent preparedEvent) {
        preparedEvent.setStatus(OrderStatus.PREPARING);
        kafkaTemplate.send("order-preparing", JsonUtils.toJson(preparedEvent));
    }

    @Override
    public void markOrderPrepared( OrderPreparedEvent preparedEvent) {
        preparedEvent.setStatus(OrderStatus.PREPARED);
        kafkaTemplate.send("order-prepared", JsonUtils.toJson(preparedEvent));
    }

    private RestaurantDTO mapToDTO(Restaurant r) {
        return RestaurantDTO.builder()
                .id(r.getId())
                .name(r.getName())
                .address(r.getAddress())
                .phone(r.getPhone())
                .build();
    }


    private Restaurant mapToEntity(RestaurantDTO dto) {
        return Restaurant.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .build();
    }
}
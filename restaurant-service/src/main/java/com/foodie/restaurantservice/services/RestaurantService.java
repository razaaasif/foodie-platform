package com.foodie.restaurantservice.services;

import com.foodie.commons.constants.RestaurantStatus;
import com.foodie.commons.dto.KeyValueDTO;
import com.foodie.commons.dto.OrderStateChangeEvent;
import com.foodie.restaurantservice.dto.RestaurantDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
public interface RestaurantService {
    RestaurantDTO save(RestaurantDTO dto);

    List<RestaurantDTO> findAll();

    RestaurantDTO findById(Long id);

    void delete(Long id);

    void markOrderPreparing( OrderStateChangeEvent preparedEvent);

    void markOrderPrepared( OrderStateChangeEvent preparedEvent);

    void markOrderConfirm(OrderStateChangeEvent orderStateChange);

    String changeStatus(@Valid @NotNull KeyValueDTO<Long, RestaurantStatus> keyValueDTO);

    String isRestaurantOpen(Long restaurantId);
}

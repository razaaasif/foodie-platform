package com.foodie.restaurantservice.services;

import com.foodie.restaurantservice.dto.OrderStateChangeEvent;
import com.foodie.restaurantservice.dto.RestaurantDTO;
import jakarta.validation.Valid;

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
}

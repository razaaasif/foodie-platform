package com.foodie.restaurantservice.services;

import com.foodie.restaurantservice.dto.OrderPreparedEvent;
import com.foodie.restaurantservice.dto.RestaurantDTO;

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

    void markOrderPreparing( OrderPreparedEvent preparedEvent);

    void markOrderPrepared( OrderPreparedEvent preparedEvent);
}

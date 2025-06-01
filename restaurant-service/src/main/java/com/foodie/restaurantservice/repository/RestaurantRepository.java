package com.foodie.restaurantservice.repository;

import com.foodie.commons.constants.RestaurantStatus;
import com.foodie.restaurantservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    void findByIdAndStatus(Long restaurantId, RestaurantStatus status);
}
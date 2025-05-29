package com.foodie.orderservice.repository;

import com.foodie.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    List<Order> findByRestaurantId(Long restaurantId);

    List<Order> findByRiderId(String riderId);
}
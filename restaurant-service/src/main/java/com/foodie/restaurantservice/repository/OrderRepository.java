package com.foodie.restaurantservice.repository;

import com.foodie.restaurantservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
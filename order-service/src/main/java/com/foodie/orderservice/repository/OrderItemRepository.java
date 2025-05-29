package com.foodie.orderservice.repository;

import com.foodie.orderservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
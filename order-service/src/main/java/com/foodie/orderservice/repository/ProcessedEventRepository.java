package com.foodie.orderservice.repository;

import com.foodie.commons.constants.OrderStatus;
import com.foodie.orderservice.entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 31/05/25.
 *
 * @author : aasif.raza
 */
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {
    boolean existsByOrderIdAndEventType(Long orderId, OrderStatus status);
}

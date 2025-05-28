package com.foodie.menuservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.foodie.menuservice.entity.MenuItem;

import java.util.List;
import java.util.UUID;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurantId(Long restaurantId);
    List<MenuItem> findByNameContainingIgnoreCase(String name);
}
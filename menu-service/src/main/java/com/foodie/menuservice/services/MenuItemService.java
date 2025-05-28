package com.foodie.menuservice.services;

import com.foodie.menuservice.dto.MenuItemDTO;

import java.util.List;
import java.util.Optional;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
public interface MenuItemService {
    MenuItemDTO save(MenuItemDTO menuItemDTO);
    List<MenuItemDTO> findAll();
    List<MenuItemDTO> getByRestaurant(Long restaurantId);
    List<MenuItemDTO> searchByName(String name);
}

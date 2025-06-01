package com.foodie.restaurantservice.services;


import com.foodie.restaurantservice.dto.MenuItemDTO;
import com.foodie.restaurantservice.dto.RestaurantMenusDTO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
public interface MenuItemService {
    List<MenuItemDTO> getByRestaurant(Long restaurantId);
    List<MenuItemDTO> searchByName(String name);
    List<MenuItemDTO> saveRestaurantMenus(RestaurantMenusDTO restaurantMenusDTO);
}

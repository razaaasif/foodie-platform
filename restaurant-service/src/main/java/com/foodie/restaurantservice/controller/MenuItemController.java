package com.foodie.restaurantservice.controller;

import com.foodie.restaurantservice.dto.MenuItemDTO;
import com.foodie.restaurantservice.dto.RestaurantMenusDTO;
import com.foodie.restaurantservice.services.MenuItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@RestController
@RequestMapping("/menus")
@AllArgsConstructor
@Slf4j
public class MenuItemController {
    private final MenuItemService service;


    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuItemDTO> getMenuByRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        return service.getByRestaurant(restaurantId);
    }

    @GetMapping("/search")
    public List<MenuItemDTO> searchMenuItems(@RequestParam String name) {
        return service.searchByName(name);
    }

    @PostMapping
    public ResponseEntity<List<MenuItemDTO>> addMenuItem(@Valid @RequestBody RestaurantMenusDTO restaurantMenusDTO) {
        log.info("addMenuItem :{}",restaurantMenusDTO);
        return ResponseEntity.ok(service.saveRestaurantMenus(restaurantMenusDTO));
    }

}

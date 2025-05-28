package com.foodie.menuservice.controller;

import com.foodie.menuservice.dto.MenuItemDTO;
import com.foodie.menuservice.services.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@RestController
@RequestMapping("/menu-items")
@AllArgsConstructor
public class MenuItemController {
    private final MenuItemService service;

    @GetMapping
    public List<MenuItemDTO> getAllMenuItems() {
        return service.findAll();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuItemDTO> getMenuByRestaurant(@PathVariable Long restaurantId) {
        return service.getByRestaurant(restaurantId);
    }

    @GetMapping("/search")
    public List<MenuItemDTO> searchMenuItems(@RequestParam String name) {
        return service.searchByName(name);
    }

    @PostMapping
    public ResponseEntity<MenuItemDTO> addMenuItem(@RequestBody MenuItemDTO menuItem) {
        return ResponseEntity.ok(service.save(menuItem));
    }

}

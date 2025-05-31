package com.foodie.restaurantservice.controller;

import com.foodie.restaurantservice.dto.OrderStateChangeEvent;
import com.foodie.restaurantservice.dto.RestaurantDTO;
import com.foodie.restaurantservice.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService service;

    @PostMapping
    public ResponseEntity<RestaurantDTO> addRestaurant(@Valid @RequestBody RestaurantDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<RestaurantDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/orders/confirm")
    public ResponseEntity<Void> markOrderConfirm(@Valid @RequestBody OrderStateChangeEvent orderStateChange) {
        service.markOrderConfirm(orderStateChange);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/preparing")
    public ResponseEntity<Void> markOrderPreparing(@Valid @RequestBody OrderStateChangeEvent orderStateChange) {
        service.markOrderPreparing(orderStateChange);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/prepared")
    public ResponseEntity<Void> markOrderPrepared(@Valid @RequestBody OrderStateChangeEvent orderStateChange) {
        service.markOrderPrepared(orderStateChange);
        return ResponseEntity.ok().build();
    }
}

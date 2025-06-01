package com.foodie.restaurantservice.controller;

import com.foodie.commons.constants.RestaurantStatus;
import com.foodie.commons.dto.KeyValueDTO;
import com.foodie.commons.dto.OrderStateChangeEvent;
import com.foodie.restaurantservice.dto.RestaurantDTO;
import com.foodie.restaurantservice.services.RestaurantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantDTO> addRestaurant(@Valid @RequestBody RestaurantDTO dto) {
        return new ResponseEntity<>(restaurantService.save(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<RestaurantDTO> getAll() {
        return restaurantService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.findById(id));
    }

    @PatchMapping("/status")
    public ResponseEntity<String> changeStatus(@Valid @NotNull @RequestBody KeyValueDTO<Long, RestaurantStatus> keyValueDTO) {
        log.debug("changeStatus() request : {}", keyValueDTO);
        return ResponseEntity.ok(this.restaurantService.changeStatus(keyValueDTO));
    }

    @GetMapping("/{restaurantId}/is-open")
    public ResponseEntity<String> isRestaurantOpen(@PathVariable("restaurantId") Long restaurantId) {
        String status = restaurantService.isRestaurantOpen(restaurantId);
        return ResponseEntity.ok(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/orders/confirm")
    public ResponseEntity<Void> markOrderConfirm(@Valid @RequestBody OrderStateChangeEvent orderStateChange) {
        restaurantService.markOrderConfirm(orderStateChange);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/preparing")
    public ResponseEntity<Void> markOrderPreparing(@Valid @RequestBody OrderStateChangeEvent orderStateChange) {
        restaurantService.markOrderPreparing(orderStateChange);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/prepared")
    public ResponseEntity<Void> markOrderPrepared(@Valid @RequestBody OrderStateChangeEvent orderStateChange) {
        restaurantService.markOrderPrepared(orderStateChange);
        return ResponseEntity.ok().build();
    }
}

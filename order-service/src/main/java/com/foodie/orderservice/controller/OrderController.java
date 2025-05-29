package com.foodie.orderservice.controller;

import com.foodie.orderservice.dto.OrderRequestDTO;
import com.foodie.orderservice.dto.OrderResponseDTO;
import com.foodie.orderservice.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@Valid @RequestBody OrderRequestDTO requestDTO, @RequestParam Long userId) {
        return ResponseEntity.ok(orderService.createOrder(requestDTO, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(orderService.getOrderById(id, userId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@RequestParam Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id, @RequestParam Long userId) {
        orderService.cancelOrder(id, userId);
        return ResponseEntity.noContent().build();
    }
}
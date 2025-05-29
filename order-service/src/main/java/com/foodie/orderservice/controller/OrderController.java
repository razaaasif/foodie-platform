package com.foodie.orderservice.controller;

import com.foodie.orderservice.dto.OrderRequestDTO;
import com.foodie.orderservice.dto.OrderResponseDTO;
import com.foodie.orderservice.services.OrderService;
import com.foodie.orderservice.utils.JsonUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@Valid @RequestBody OrderRequestDTO requestDTO, @RequestParam Long userId) {
        log.info("placeOrder() order :{}", JsonUtils.toJson(requestDTO));
        return ResponseEntity.ok(orderService.createOrder(requestDTO, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
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


    @GetMapping("/riderId/{orderId}")
    public ResponseEntity<String> getRiderIdByOrderId(@NotNull @PathVariable Long orderId){
        return ResponseEntity.ok(orderService.getAssignedRiderForOrder(orderId));
    }
}
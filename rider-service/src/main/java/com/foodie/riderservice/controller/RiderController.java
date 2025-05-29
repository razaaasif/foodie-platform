package com.foodie.riderservice.controller;

import com.foodie.riderservice.dto.DeliverOrderRequest;
import com.foodie.riderservice.services.RiderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@RestController
@RequestMapping("/api/rider")
@RequiredArgsConstructor
@Slf4j
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/take-order")
    public ResponseEntity<String> outForDelivery(@RequestBody DeliverOrderRequest request) {
        riderService.takeOrder(request.getOrderId(), request.getRiderId(), request.getDeliveryTime());
        return ResponseEntity.ok("Out for delivery");
    }
    @PostMapping("/deliver")
    public ResponseEntity<String> deliverOrder(@RequestBody DeliverOrderRequest request) {
        riderService.deliverOrder(request.getOrderId(), request.getRiderId(), request.getDeliveryTime());
        return ResponseEntity.ok("Delivery recorded successfully");
    }
}
package com.foodie.riderservice.controller;

import com.foodie.commons.utils.JsonUtils;
import com.foodie.riderservice.dto.DeliverOrderRequest;
import com.foodie.riderservice.dto.RiderDetailDTO;
import com.foodie.riderservice.services.RiderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@RestController
@RequestMapping("/riders")
@RequiredArgsConstructor
@Slf4j
public class RiderController {

    private final RiderService riderService;

    @PostMapping
    public ResponseEntity<RiderDetailDTO> createRider(@RequestBody @Valid RiderDetailDTO riderDetailDTO) {
        log.info("createRider() with :{}", JsonUtils.toJson(riderDetailDTO));
        return new ResponseEntity<>(this.riderService.createRider(riderDetailDTO), HttpStatus.CREATED);
    }


    @PostMapping("/pickup")
    public ResponseEntity<String> outForDelivery(@RequestBody DeliverOrderRequest request) {
        riderService.takeOrder(request.getOrderId(), request.getRiderId());
        return ResponseEntity.ok("Out for delivery");
    }

    @PostMapping("/deliver")
    public ResponseEntity<String> deliverOrder(@RequestBody DeliverOrderRequest request) {
        riderService.deliverOrder(request.getOrderId(), request.getRiderId());
        return ResponseEntity.ok("Order delivered successfully!");
    }
}
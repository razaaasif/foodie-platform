package com.foodie.paymentservice.controller;

import com.foodie.commons.constants.PaymentMethod;
import com.foodie.commons.dto.PaymentStatusUpdateEventDTO;
import com.foodie.paymentservice.dto.PaymentConfirmationRequestDTO;
import com.foodie.paymentservice.dto.PaymentGateWayDTO;
import com.foodie.paymentservice.services.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@RequestMapping("/payments")
@AllArgsConstructor
@RestController
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment-gateway")
    public ResponseEntity<String> getPaymentGateway(@Valid @RequestBody PaymentGateWayDTO paymentGateWayDTO) {
        return ResponseEntity.ok(this.paymentService.generateRedirectUrl(PaymentMethod.getPaymentMethodWithName(paymentGateWayDTO.getPaymentMethod()), paymentGateWayDTO.getOrderId()));
    }

    @PostMapping("/process-payment")
    public ResponseEntity<PaymentStatusUpdateEventDTO> processPayment(@Valid @RequestBody PaymentConfirmationRequestDTO paymentConfirmationRequestDTO) {
        return ResponseEntity.ok(paymentService.processPayment(paymentConfirmationRequestDTO.getAmount(), paymentConfirmationRequestDTO.getTransactionId()));
    }

}

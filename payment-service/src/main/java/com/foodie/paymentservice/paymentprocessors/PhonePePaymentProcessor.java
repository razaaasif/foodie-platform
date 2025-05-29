package com.foodie.paymentservice.paymentprocessors;

import com.foodie.paymentservice.constants.PaymentMethod;
import com.foodie.paymentservice.constants.PaymentStatus;
import com.foodie.paymentservice.dto.PaymentConfirmationRequest;
import com.foodie.paymentservice.dto.PaymentInitiateRequest;
import com.foodie.paymentservice.dto.PaymentInitiateResponse;
import com.foodie.paymentservice.entity.Payment;
import com.foodie.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Component
public class PhonePePaymentProcessor implements PaymentProcessor {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) {
        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setMethod(PaymentMethod.PHONE_PE);
        payment.setStatus(PaymentStatus.INITIATED);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString());
        paymentRepository.save(payment);

        return new PaymentInitiateResponse(payment.getTransactionId(), "/phone-pe/redirect",PaymentMethod.PHONE_PE);
    }

    @Override
    public String generateRedirectUrl(String transactionId) {
        return "/phone-pe/redirect?transactionId=" + transactionId;
    }

    @Override
    public String confirmPayment(PaymentConfirmationRequest request) {
        Payment payment = paymentRepository.findByTransactionId(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        payment.setStatus(request.isSuccess() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
        paymentRepository.save(payment);

        return "PhonePe Payment " + payment.getStatus();
    }
}
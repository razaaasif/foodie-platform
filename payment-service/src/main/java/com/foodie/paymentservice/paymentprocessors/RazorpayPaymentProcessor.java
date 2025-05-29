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
public class RazorpayPaymentProcessor implements PaymentProcessor {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) {
        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setMethod(PaymentMethod.RAZORPAY);
        payment.setStatus(PaymentStatus.INITIATED);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString());
        paymentRepository.save(payment);

        return new PaymentInitiateResponse(payment.getOrderId(),payment.getTransactionId(), "/razor-pe/redirect",PaymentMethod.BHARAT_PE);
    }

    @Override
    public String generateRedirectUrl(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return "/razor-pe/redirect?transactionId=" + payment.getTransactionId() + "?amount="+payment.getAmount();
    }

    @Override
    public String confirmPayment(PaymentConfirmationRequest request) {
        Payment payment = paymentRepository.findByTransactionId(request.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        payment.setStatus(request.isSuccess() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
        paymentRepository.save(payment);

        return "Razorpay Payment " + payment.getStatus();  // <-- change label per service
    }
}

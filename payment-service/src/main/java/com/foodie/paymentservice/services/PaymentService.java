package com.foodie.paymentservice.services;

import com.foodie.commons.constants.PaymentMethod;
import com.foodie.commons.constants.PaymentStatus;
import com.foodie.commons.dto.PaymentStatusUpdateEventDTO;
import com.foodie.paymentservice.dto.PaymentConfirmationRequest;
import com.foodie.paymentservice.dto.PaymentInitiateRequest;
import com.foodie.paymentservice.dto.PaymentInitiateResponse;
import com.foodie.paymentservice.entity.Payment;
import com.foodie.paymentservice.paymentprocessors.PaymentProcessor;
import com.foodie.paymentservice.paymentprocessors.PaymentProcessorFactory;
import com.foodie.paymentservice.repository.PaymentRepository;
import com.foodie.paymentservice.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentProcessorFactory paymentProcessors;
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String PAYMENT_STATUS_UPDATED_TOPIC = "payment-status-updated";

    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) {
        PaymentProcessor processor = paymentProcessors.getProcessor(request.getPaymentMethod());
        if (processor == null) {
            throw new RuntimeException("Unsupported payment method: " + request.getPaymentMethod());
        }
        return processor.initiatePayment(request);
    }

    public String generateRedirectUrl(PaymentMethod method, Long orderId) {
        PaymentProcessor processor = paymentProcessors.getProcessor(method);
        if (processor == null) {
            throw new RuntimeException("Unsupported payment method: " + method);
        }
        return processor.generateRedirectUrl(orderId);
    }

    public String confirmPayment(PaymentConfirmationRequest request) {
        PaymentProcessor processor = paymentProcessors.getProcessor(request.getPaymentMethod());
        if (processor == null) {
            throw new RuntimeException("Unsupported payment method: " + request.getPaymentMethod());
        }
        return processor.confirmPayment(request);
    }

    public PaymentStatusUpdateEventDTO processPayment(BigDecimal bigDecimal, String transactionId) {

        //here we can fetch from user service
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        if (payment.getAmount().compareTo(bigDecimal) == 0) {
            payment.setStatus(PaymentStatus.SUCCESS);
            log.info("Payment successful for amount: {}", payment.getAmount());
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            log.warn("Payment amount mismatch. Expected: {}, Actual: {}", bigDecimal, payment.getAmount());
        }
        paymentRepository.save(payment);
        PaymentStatusUpdateEventDTO paymentStatusUpdatedEvent = mapToPaymentStatusUpdatedEventDTO(payment);
        kafkaTemplate.send(PAYMENT_STATUS_UPDATED_TOPIC, JsonUtils.toJson(paymentStatusUpdatedEvent));
        return paymentStatusUpdatedEvent;
    }

    private PaymentStatusUpdateEventDTO mapToPaymentStatusUpdatedEventDTO(Payment payment) {
        PaymentStatusUpdateEventDTO paymentStatusUpdatedEvent = new PaymentStatusUpdateEventDTO();
        paymentStatusUpdatedEvent.setOrderId(payment.getOrderId());
        paymentStatusUpdatedEvent.setPaymentStatus(payment.getStatus());
        paymentStatusUpdatedEvent.setPaymentMethod(payment.getMethod());
        paymentStatusUpdatedEvent.setTransactionId(payment.getTransactionId());
        paymentStatusUpdatedEvent.setAmount(payment.getAmount());
        return paymentStatusUpdatedEvent;
    }
}

package com.foodie.paymentservice.kafkaservices;


import com.foodie.paymentservice.constants.PaymentStatus;
import com.foodie.paymentservice.dto.*;
import com.foodie.paymentservice.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Service
@AllArgsConstructor
public class PaymentEventConsumer {

    private final PaymentService paymentService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String PAYMENT_STATUS_UPDATED_TOPIC = "payment-status-updated";

    @KafkaListener(topics = "order-created", groupId = "payment-service-group")
    public void onOrderCreated(OrderCreatedEvent event) {
        // Simulate payment initiation
        try {
            // Build a mock payment initiation request
            PaymentInitiateRequest request = new PaymentInitiateRequest();
            request.setOrderId(event.getOrderId());
            request.setAmount(event.getTotalAmount());
            request.setPaymentMethod(event.getPaymentMethod()); // Assume credit card for demo

            PaymentInitiateResponse response = paymentService.initiatePayment(request);

            // Simulate payment success confirmation immediately for demo
            PaymentConfirmationRequest confirmationRequest = new PaymentConfirmationRequest();
            confirmationRequest.setOrderId(event.getOrderId());
            confirmationRequest.setPaymentMethod(response.getPaymentMethod());
            confirmationRequest.setTransactionId(response.getTransactionId());

            String paymentStatus = paymentService.confirmPayment(confirmationRequest);

            // Publish payment status event
            kafkaTemplate.send(PAYMENT_STATUS_UPDATED_TOPIC, new PaymentStatusUpdatedEvent(event.getOrderId(), paymentStatus));
        } catch (Exception e) {
            // On exception, send FAILED status
            kafkaTemplate.send(PAYMENT_STATUS_UPDATED_TOPIC, new PaymentStatusUpdatedEvent(event.getOrderId(), PaymentStatus.FAILED.name()));
        }
    }
}

package com.foodie.paymentservice.kafkaservices;


import com.foodie.paymentservice.constants.PaymentStatus;
import com.foodie.paymentservice.dto.*;
import com.foodie.paymentservice.services.PaymentService;
import com.foodie.paymentservice.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PaymentEventConsumer {

    private final PaymentService paymentService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String PAYMENT_STATUS_UPDATED_TOPIC = "payment-status-updated";

    @KafkaListener(topics = "order-created", groupId = "payment-service-group")
    public void onOrderCreated(String orderData) {
        log.debug("onOrderCreated() orderData :{}", orderData);
        OrderCreatedEvent event = JsonUtils.fromJson(orderData, OrderCreatedEvent.class);

        PaymentInitiateRequest request = new PaymentInitiateRequest();
        request.setOrderId(event.getOrderId());
        request.setAmount(event.getTotalAmount());
        request.setPaymentMethod(event.getPaymentMethod());

        PaymentInitiateResponse response = paymentService.initiatePayment(request);
        log.debug("onOrderCreated() initiation response :{}",JsonUtils.toJson(response));
        try {
            PaymentStatusUpdatedEvent paymentStatusUpdatedEvent = new PaymentStatusUpdatedEvent();
            paymentStatusUpdatedEvent.setOrderId(request.getOrderId());
            paymentStatusUpdatedEvent.setPaymentStatus(PaymentStatus.INITIATED);
            paymentStatusUpdatedEvent.setTransactionId(response.getTransactionId());
            paymentStatusUpdatedEvent.setPaymentMethod(response.getPaymentMethod());
            paymentStatusUpdatedEvent.setAmount(event.getTotalAmount());
            String currentStatus = JsonUtils.toJson(paymentStatusUpdatedEvent);
            kafkaTemplate.send(PAYMENT_STATUS_UPDATED_TOPIC, currentStatus);
            log.debug("onOrderCreated() sent payment status  :{} successfully", currentStatus);

        } catch (Exception exception) {
            log.error("onOrderCreated() response {}", response);
            exception.printStackTrace();
        }
    }
}

package com.foodie.paymentservice.dto;

import com.foodie.paymentservice.constants.PaymentMethod;
import com.foodie.paymentservice.constants.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentStatusUpdatedEvent {
    private Long orderId;
    private PaymentStatus paymentStatus;
    private BigDecimal amount;
    private String transactionId;
    private PaymentMethod paymentMethod;
    public PaymentStatusUpdatedEvent(Long orderId, PaymentMethod paymentMethod,BigDecimal totalAmount) {
        this.orderId = orderId;
        this.amount = totalAmount;
        this.paymentMethod = paymentMethod;
    }
}
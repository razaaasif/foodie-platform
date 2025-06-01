package com.foodie.commons.dto;

import com.foodie.commons.constants.PaymentMethod;
import com.foodie.commons.constants.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusUpdateEventDTO {
    private Long orderId;
    private PaymentStatus paymentStatus;
    private BigDecimal amount;
    private String transactionId;
    private PaymentMethod paymentMethod;
    private String redirectUrl;
    public PaymentStatusUpdateEventDTO(Long orderId, PaymentMethod paymentMethod,BigDecimal totalAmount) {
        this.orderId = orderId;
        this.amount = totalAmount;
        this.paymentMethod = paymentMethod;
    }
}
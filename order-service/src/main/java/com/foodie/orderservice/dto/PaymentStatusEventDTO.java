package com.foodie.orderservice.dto;

import com.foodie.orderservice.constants.PaymentMethod;
import com.foodie.orderservice.constants.PaymentStatus;
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
public class PaymentStatusEventDTO {
    private Long orderId;
    private PaymentStatus paymentStatus;
    private BigDecimal amount;
    private String transactionId;
    private PaymentMethod paymentMethod;
    private String redirectUrl;
}
package com.foodie.orderservice.dto;

import com.foodie.orderservice.constants.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Data
@Builder
public class PaymentStatusEventDTO {
    private Long orderId;
    private PaymentStatus status;
    private BigDecimal amount;
    private String transactionId;
}
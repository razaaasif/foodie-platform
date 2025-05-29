package com.foodie.orderservice.dto;

import com.foodie.orderservice.constants.PaymentMethod;
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
public class PaymentRequestDTO{
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
}

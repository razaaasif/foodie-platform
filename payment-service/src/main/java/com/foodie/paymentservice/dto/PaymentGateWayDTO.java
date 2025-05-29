package com.foodie.paymentservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Data
public class PaymentGateWayDTO {
    @NotNull(message = "Payment method is required")
    private String paymentMethod;

    @NotNull(message = "Order ID is required")
    private Long orderId;
}

package com.foodie.paymentservice.dto;

import jakarta.validation.constraints.NotNull;
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
public class PaymentConfirmationRequestDTO {
    @NotNull(message = "Payment amount is required")
    private BigDecimal amount;
    @NotNull(message = "Transaction ID is required")
    private String transactionId;
}

package com.foodie.paymentservice.dto;

import com.foodie.commons.constants.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfirmationRequest {
    private String transactionId;
    private boolean success;
    private PaymentMethod paymentMethod;
    private Long orderId;
}

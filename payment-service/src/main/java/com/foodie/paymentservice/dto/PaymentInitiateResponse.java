package com.foodie.paymentservice.dto;

import com.foodie.paymentservice.constants.PaymentMethod;
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
public class PaymentInitiateResponse {
    private String transactionId;
    private String redirectUrl;
    private PaymentMethod paymentMethod;

}

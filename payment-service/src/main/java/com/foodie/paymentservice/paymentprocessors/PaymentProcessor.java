package com.foodie.paymentservice.paymentprocessors;

import com.foodie.paymentservice.dto.PaymentConfirmationRequest;
import com.foodie.paymentservice.dto.PaymentInitiateRequest;
import com.foodie.paymentservice.dto.PaymentInitiateResponse;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
public interface PaymentProcessor {
    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request);
    String generateRedirectUrl(String transactionId);
    String confirmPayment(PaymentConfirmationRequest request);
}

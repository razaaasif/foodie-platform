package com.foodie.paymentservice.services;

import com.foodie.paymentservice.constants.PaymentMethod;
import com.foodie.paymentservice.dto.PaymentConfirmationRequest;
import com.foodie.paymentservice.dto.PaymentInitiateRequest;
import com.foodie.paymentservice.dto.PaymentInitiateResponse;
import com.foodie.paymentservice.paymentprocessors.PaymentProcessor;
import com.foodie.paymentservice.paymentprocessors.PaymentProcessorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentProcessorFactory paymentProcessors;


    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) {
        PaymentProcessor processor = paymentProcessors.getProcessor(request.getPaymentMethod());
        if (processor == null) {
            throw new RuntimeException("Unsupported payment method: " + request.getPaymentMethod());
        }
        return processor.initiatePayment(request);
    }

    public String generateRedirectUrl(PaymentMethod method, String transactionId) {
        PaymentProcessor processor = paymentProcessors.getProcessor(method);
        if (processor == null) {
            throw new RuntimeException("Unsupported payment method: " + method);
        }
        return processor.generateRedirectUrl(transactionId);
    }

    public String confirmPayment(PaymentConfirmationRequest request) {
        PaymentProcessor processor = paymentProcessors.getProcessor(request.getPaymentMethod());
        if (processor == null) {
            throw new RuntimeException("Unsupported payment method: " + request.getPaymentMethod());
        }
        return processor.confirmPayment(request);
    }
}

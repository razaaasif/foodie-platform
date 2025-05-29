package com.foodie.paymentservice.paymentprocessors;

import com.foodie.paymentservice.constants.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Component
public class PaymentProcessorFactory {
    @Autowired
    private MockPaymentProcessor mockPaymentService;

    @Autowired
    private RazorpayPaymentProcessor razorpayPaymentService;

    @Autowired
    private BharatPePaymentProcessor bharatPePaymentService;

    @Autowired
    private PhonePePaymentProcessor phonePePaymentService;

    public PaymentProcessor getProcessor(PaymentMethod method) {
        return switch (method) {
            case RAZORPAY -> razorpayPaymentService;
            case BHARAT_PE -> bharatPePaymentService;
            case PHONE_PE -> phonePePaymentService;
            default -> mockPaymentService;
        };
    }
}

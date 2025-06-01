package com.foodie.commons.constants;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
public enum PaymentMethod {
    RAZORPAY, BHARAT_PE, PHONE_PE, MOCK;

    public static PaymentMethod getPaymentMethodWithName(String name) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.name().equals(name)) {
                return paymentMethod;
            }
        }
        throw new IllegalArgumentException("Given payment method is not valid");
    }
}

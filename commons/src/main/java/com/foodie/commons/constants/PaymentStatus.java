package com.foodie.commons.constants;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
public enum PaymentStatus {
    SUCCESS,
    FAILED, INITIATED, NOT_STARTED;

    public static PaymentStatus getPaymentStatusByName(String status) {
        for (PaymentStatus payment : PaymentStatus.values()) {
            if (payment.name().equalsIgnoreCase(status)) {
                return payment;
            }
        }
        throw new IllegalArgumentException("Payment status is not valid : " + status);
    }
}

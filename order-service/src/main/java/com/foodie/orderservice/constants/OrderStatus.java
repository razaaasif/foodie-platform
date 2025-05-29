package com.foodie.orderservice.constants;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
public enum OrderStatus {
    CREATED,
    PAYMENT_PENDING,
    PAYMENT_COMPLETED,
    PAYMENT_FAILED,
    RESTAURANT_CONFIRMED,
    PREPARING,
    READY_FOR_PICKUP,
    PICKED_UP,
    ON_THE_WAY,
    DELIVERED,
    PREPARED, OUT_FOR_DELIVERY, CANCELLED

}

package com.foodie.restaurantservice.constants;

/**
 * Created on 29/05/25.
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
    PREPARED,
    CANCELLED
}

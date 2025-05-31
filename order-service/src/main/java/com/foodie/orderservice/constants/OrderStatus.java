package com.foodie.orderservice.constants;

import java.util.EnumSet;

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
    PAYMENT_FAILED_CANCELLED,
    RESTAURANT_CONFIRMED,
    PREPARING,
    PREPARED,
    RIDER_ASSIGN,
    PICKED_UP,
    ON_THE_WAY,
    DELIVERED,
    CANCELLED, READY_TO_PICK_UP;

    private static final EnumSet<OrderStatus> deadState =
            EnumSet.of(DELIVERED, CANCELLED, PAYMENT_FAILED_CANCELLED);

    public static boolean isTerminatedState(OrderStatus status) {
        return deadState.contains(status);
    }
}

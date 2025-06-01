package com.foodie.orderservice.validators;


import com.foodie.commons.constants.OrderStatus;

import java.util.Map;
import java.util.Set;

/**
 * Created on 31/05/25.
 *
 * @author : aasif.raza
 */
public class OrderStatusTransitionValidator {

    private static final Map<OrderStatus, Set<OrderStatus>> validTransitions = Map.ofEntries(
            Map.entry(OrderStatus.CREATED, Set.of(
                    OrderStatus.PAYMENT_PENDING,
                    OrderStatus.PAYMENT_FAILED
            )),
            Map.entry(OrderStatus.PAYMENT_PENDING, Set.of(
                    OrderStatus.PAYMENT_COMPLETED,
                    OrderStatus.PAYMENT_FAILED
            )),
            Map.entry(OrderStatus.PAYMENT_COMPLETED, Set.of(
                    OrderStatus.RESTAURANT_CONFIRMED,
                    OrderStatus.CANCELLED
            )),
            Map.entry(OrderStatus.PAYMENT_FAILED, Set.of(
                    OrderStatus.PAYMENT_FAILED_CANCELLED
            )),
            Map.entry(OrderStatus.RESTAURANT_CONFIRMED, Set.of(
                    OrderStatus.PREPARING,
                    OrderStatus.CANCELLED
            )),
            Map.entry(OrderStatus.PREPARING, Set.of(
                    OrderStatus.PREPARED,
                    OrderStatus.CANCELLED
            )),
            Map.entry(OrderStatus.PREPARED, Set.of(
                    OrderStatus.RIDER_ASSIGN,
                    OrderStatus.CANCELLED
            )),
            Map.entry(OrderStatus.RIDER_ASSIGN, Set.of(
                    OrderStatus.PICKED_UP,
                    OrderStatus.ON_THE_WAY,
                    OrderStatus.CANCELLED
            )),
            Map.entry(OrderStatus.PICKED_UP, Set.of(
                    OrderStatus.DELIVERED,
                    OrderStatus.CANCELLED
            ))
    );

    public static void validateTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        Set<OrderStatus> allowedStatuses = validTransitions.get(currentStatus);
        if (allowedStatuses == null || !allowedStatuses.contains(newStatus)) {
            throw new IllegalStateException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }
    }
}
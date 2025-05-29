package com.foodie.orderservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Getter
public class OrderException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorType;

    public OrderException(String message, HttpStatus httpStatus, String errorType) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorType = errorType;
    }

    // Common factory methods for different error scenarios
    public static OrderException notFound(Long orderId) {
        return new OrderException(
                "Order not found with id: " + orderId,
                HttpStatus.NOT_FOUND,
                "ORDER_NOT_FOUND"
        );
    }

    public static OrderException unauthorized(String action) {
        return new OrderException(
                "You are not authorized to " + action + " this order",
                HttpStatus.FORBIDDEN,
                "UNAUTHORIZED_ACCESS"
        );
    }

    public static OrderException validationFailed(String message) {
        return new OrderException(
                message,
                HttpStatus.BAD_REQUEST,
                "VALIDATION_FAILED"
        );
    }

    public static OrderException conflict(String message) {
        return new OrderException(
                message,
                HttpStatus.CONFLICT,
                "ORDER_CONFLICT"
        );
    }
}
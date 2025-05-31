package com.foodie.orderservice.exception;

/**
 * Created on 31/05/25.
 *
 * @author : aasif.raza
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}

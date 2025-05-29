package com.foodie.orderservice.dto;

import com.foodie.orderservice.constants.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPreparedEvent {
    private Long orderId;
    private Long restaurantId;
    private OrderStatus status;
    private String deliveryAddress;
}
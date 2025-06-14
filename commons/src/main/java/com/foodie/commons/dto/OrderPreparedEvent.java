package com.foodie.commons.dto;

import com.foodie.commons.constants.OrderStatus;
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
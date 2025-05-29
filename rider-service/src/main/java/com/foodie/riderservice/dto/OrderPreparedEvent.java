package com.foodie.riderservice.dto;

import com.foodie.riderservice.constants.OrderStatus;
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
    private String orderId;
    private String restaurantId;
    private OrderStatus status;
}
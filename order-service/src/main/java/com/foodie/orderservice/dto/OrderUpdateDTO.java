package com.foodie.orderservice.dto;

import com.foodie.orderservice.constants.OrderStatus;
import lombok.Data;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Data
public class OrderUpdateDTO {
    private Long orderId;
    private OrderStatus orderStatus;
    private String riderId ;
}

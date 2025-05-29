package com.foodie.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliverOrderRequest {
    private String orderId;
    private String riderId;
    private String deliveryTime;
}

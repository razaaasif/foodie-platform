package com.foodie.riderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDeliveredEvent {
    private String orderId;
    private String riderId;
    private String deliveryTime;
}

package com.foodie.commons.dto;

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
    private Long orderId;
    private String riderId;
    private String deliveryTime;
}

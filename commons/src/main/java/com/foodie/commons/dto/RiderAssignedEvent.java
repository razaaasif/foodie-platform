package com.foodie.commons.dto;

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
public class RiderAssignedEvent {
    private Long orderId;
    private String riderId;
    private String eta;
    private String deliveryAddress;
}
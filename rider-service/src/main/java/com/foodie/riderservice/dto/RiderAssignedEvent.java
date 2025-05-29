package com.foodie.riderservice.dto;

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
    private String orderId;
    private String riderId;
    private String eta;
}
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
@NoArgsConstructor
@AllArgsConstructor
public class DeliverOrderRequest {
    private Long orderId;
    private String riderId;
}

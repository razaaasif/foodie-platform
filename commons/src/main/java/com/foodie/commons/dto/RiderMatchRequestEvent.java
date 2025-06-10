package com.foodie.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 07/06/25.
 *
 * @author : aasif.raza
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderMatchRequestEvent {
    private Long orderId;
    private String pinCode;
}

package com.foodie.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class OrderStateChangeEvent {
    private Long orderId;
    private Long restaurantId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrderStatus status;
}
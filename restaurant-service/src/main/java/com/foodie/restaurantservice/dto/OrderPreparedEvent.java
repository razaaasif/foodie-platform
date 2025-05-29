package com.foodie.restaurantservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.foodie.restaurantservice.constants.OrderStatus;
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
    private Long orderId;
    private Long restaurantId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrderStatus status;
}
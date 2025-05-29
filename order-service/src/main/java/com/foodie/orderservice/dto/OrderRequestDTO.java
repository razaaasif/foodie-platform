package com.foodie.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    @NotNull(message = "Restaurant ID cannot be null")
    private Long restaurantId;

    @NotEmpty(message = "Order items cannot be empty")
    private List<OrderItemDTO> items;

    @NotNull(message = "Delivery address cannot be null")
    private String deliveryAddress;
}
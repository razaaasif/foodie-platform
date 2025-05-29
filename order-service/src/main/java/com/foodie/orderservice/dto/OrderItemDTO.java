package com.foodie.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    private Long menuItemId;
    private Integer quantity;
    private BigDecimal pricePerItem;
    private String specialInstructions;
}

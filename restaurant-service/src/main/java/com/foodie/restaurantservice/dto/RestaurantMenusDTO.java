package com.foodie.restaurantservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created on 01/06/25.
 *
 * @author : aasif.raza
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantMenusDTO {
    @NotNull(message = "Restaurant ID cannot be null")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long restaurantId;

    @NotNull(message = "Please add at-least one menu item.")
    private List<MenuItemDTO> menus;
}

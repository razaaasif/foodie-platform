package com.foodie.menuservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuItemDTO {
    private Long id;
    @NotNull(message = "Restaurant ID cannot be null")
    private Long restaurantId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name can't exceed 100 characters")
    private String name;

    @Size(max = 255, message = "Description can't exceed 255 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotBlank(message = "Category is required")
    private String category;

    private Boolean available = true;
}

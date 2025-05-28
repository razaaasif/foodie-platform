package com.foodie.restaurantservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDTO {
    private Long id;

    @NotBlank(message = "Restaurant name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone number is required")
    private String phone;
}
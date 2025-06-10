package com.foodie.riderservice.dto;

import com.foodie.commons.constants.RiderStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

/**
 * Created on 07/06/25.
 *
 * @author : aasif.raza
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderDetailDTO {
    private String id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Pin code is required")
    @Size(min = 6, max = 6, message = "Pin code must be 6 digits")
    private String pinCode;
    private RiderStatus riderStatus;
}

package com.foodie.userservice.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
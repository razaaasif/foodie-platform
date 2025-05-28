package com.foodie.restaurantservice.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */

@Entity(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phone;
}
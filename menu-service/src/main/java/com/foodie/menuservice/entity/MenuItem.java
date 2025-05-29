package com.foodie.menuservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */

@Entity
@Getter
@Setter
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long restaurantId;
    private String name;
    private String description;
    private Double price;
    private String category;
    private boolean available;
}


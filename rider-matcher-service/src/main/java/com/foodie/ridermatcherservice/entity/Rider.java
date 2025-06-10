package com.foodie.ridermatcherservice.entity;

import com.foodie.commons.constants.RiderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Entity(name = "riders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String pinCode;
    @Enumerated(EnumType.STRING)
    private RiderStatus riderStatus;
    @Column(nullable = false)
    private Instant createdOn;
    @Column(nullable = false)
    private Instant updatedOn;

    @PrePersist
    protected void onCreate() {
        createdOn = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedOn = Instant.now();
    }

}

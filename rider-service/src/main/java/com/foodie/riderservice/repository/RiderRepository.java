package com.foodie.riderservice.repository;

import com.foodie.riderservice.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created on 07/06/25.
 *
 * @author : aasif.raza
 */
public interface RiderRepository extends JpaRepository<Rider, String> {
    Optional<Rider> findByPhone(String phone);
}

package com.foodie.ridermatcherservice.repository;

import com.foodie.commons.constants.RiderStatus;
import com.foodie.ridermatcherservice.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created on 07/06/25.
 *
 * @author : aasif.raza
 */
public interface RiderRepository extends JpaRepository<Rider, String> {
    Optional<Rider> findByPhone(String phone);
    List<Rider> findByPinCodeAndRiderStatus(String pinCode, RiderStatus riderStatus);
}

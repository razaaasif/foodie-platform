package com.foodie.userservice.repository;

import com.foodie.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}

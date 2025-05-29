package com.foodie.paymentservice.repository;

import com.foodie.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTransactionId(String transactionId);
}
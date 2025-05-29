package com.foodie.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusUpdatedEvent {
    private Long orderId;
    private String paymentStatus; // SUCCESS or FAILED
}
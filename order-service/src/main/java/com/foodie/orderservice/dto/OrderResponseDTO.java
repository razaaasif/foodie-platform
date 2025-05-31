package com.foodie.orderservice.dto;

import com.foodie.orderservice.constants.OrderStatus;
import com.foodie.orderservice.constants.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private Long userId;
    private Long restaurantId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime orderTime;
    private LocalDateTime deliveryTime;
    private String deliveryAddress;
    private String riderId;
    private String transactionId;
    private PaymentStatus paymentStatus;
    private String paymentTime;
    private String deliveredOn;
    private boolean delivered;
    private List<OrderItemDTO> items;
}
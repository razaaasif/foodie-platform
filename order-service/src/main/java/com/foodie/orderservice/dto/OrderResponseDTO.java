package com.foodie.orderservice.dto;

import com.foodie.orderservice.constants.OrderStatus;
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
    private Long id;
    private Long userId;
    private Long restaurantId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime orderTime;
    private LocalDateTime deliveryTime;
    private String deliveryAddress;
    private Long riderId;
    private String paymentId;
    private List<OrderItemDTO> items;
}
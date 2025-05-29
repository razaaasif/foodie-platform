package com.foodie.orderservice.dto;

import com.foodie.orderservice.constants.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;
    private List<OrderItemEvent> items;
    private PaymentMethod paymentMethod;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemEvent {
        private Long menuItemId;
        private int quantity;
        private BigDecimal pricePerItem;
    }
}
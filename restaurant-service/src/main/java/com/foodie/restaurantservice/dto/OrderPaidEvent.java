package com.foodie.restaurantservice.dto;

import java.math.BigDecimal;

public record OrderPaidEvent(Long id, Long restaurantId, Long userId, BigDecimal totalAmount) {
}

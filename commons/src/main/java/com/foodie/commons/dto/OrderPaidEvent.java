package com.foodie.commons.dto;

import java.math.BigDecimal;

public record OrderPaidEvent(Long id, Long restaurantId, Long userId, BigDecimal totalAmount) {
}

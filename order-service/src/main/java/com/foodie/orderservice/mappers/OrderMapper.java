package com.foodie.orderservice.mappers;

import com.foodie.commons.constants.OrderStatus;
import com.foodie.commons.constants.PaymentStatus;
import com.foodie.commons.dto.OrderCreatedEvent;
import com.foodie.orderservice.dto.OrderItemDTO;
import com.foodie.orderservice.dto.OrderRequestDTO;
import com.foodie.orderservice.dto.OrderResponseDTO;
import com.foodie.orderservice.entity.Order;
import com.foodie.orderservice.entity.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 31/05/25.
 *
 * @author : aasif.raza
 */
public class OrderMapper {
    public static OrderResponseDTO mapToOrderResponseDto(Order order) {
        List<OrderItemDTO> itemDtos = order.getItems().stream()
                .map(OrderMapper::mapToOrderItemDto)
                .collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .restaurantId(order.getRestaurantId())
                .status(getUserBasedStatus(order.getStatus()))
                .totalAmount(order.getTotalAmount())
                .orderTime(order.getOrderTime())
                .deliveryTime(order.getDeliveryTime())
                .deliveryAddress(order.getDeliveryAddress())
                .riderId(order.getRiderId())
                .transactionId(order.getTransactionId())
                .items(itemDtos)
                .paymentStatus(order.getPaymentStatus())
                .paymentTime(order.getPaymentTime() != null? order.getPaymentTime().toString():null)
                .delivered(order.getStatus() == OrderStatus.DELIVERED)
                .deliveredOn(order.getStatus() == OrderStatus.DELIVERED ? order.getUpdatedOn().toString() : null)
                .build();
    }

    private static OrderStatus getUserBasedStatus(OrderStatus status) {
        switch (status){
            case PICKED_UP -> {
                return OrderStatus.ON_THE_WAY;
            }
            case PREPARED -> {
                return OrderStatus.READY_TO_PICK_UP;
            }
        }
        return status;
    }

    public static OrderItemDTO mapToOrderItemDto(OrderItem item) {
        return OrderItemDTO.builder()
                .menuItemId(item.getMenuItemId())
                .quantity(item.getQuantity())
                .pricePerItem(item.getPricePerItem())
                .specialInstructions(item.getSpecialInstructions())
                .build();
    }

    public static OrderCreatedEvent mapToOrderCreated(Order savedOrder) {
        return OrderCreatedEvent.builder()
                .orderId(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .totalAmount(savedOrder.getTotalAmount())
                .paymentMethod(savedOrder.getPaymentMethod())
                .items(savedOrder.getItems().stream()
                        .map(orderItem -> new OrderCreatedEvent.OrderItemEvent(
                                orderItem.getMenuItemId(),
                                orderItem.getQuantity(),
                                orderItem.getPricePerItem()
                        ))
                        .toList())
                .build();
    }

    public static Order createOrderFromOrderRequest(OrderRequestDTO orderRequest, Long userId, BigDecimal totalAmount) {
        return Order.builder()
                .userId(userId)
                .restaurantId(orderRequest.getRestaurantId())
                .status(OrderStatus.CREATED)
                .totalAmount(totalAmount)
                .orderTime(LocalDateTime.now())
                .paymentMethod(orderRequest.getPaymentMethod())
                .deliveryAddress(orderRequest.getDeliveryAddress())
                .paymentStatus(PaymentStatus.NOT_STARTED)
                .build();
    }
}

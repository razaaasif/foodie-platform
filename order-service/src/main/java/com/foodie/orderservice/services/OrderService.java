package com.foodie.orderservice.services;

import com.foodie.commons.dto.PaymentStatusUpdateEventDTO;
import com.foodie.orderservice.dto.OrderRequestDTO;
import com.foodie.orderservice.dto.OrderResponseDTO;
import com.foodie.orderservice.dto.OrderUpdateDTO;
import com.foodie.orderservice.exception.OrderException;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequest, Long userId);
    OrderResponseDTO getOrderById(Long orderId) throws OrderException;
    List<OrderResponseDTO> getOrdersByUser(Long userId);
    void updateOrderStatus(OrderUpdateDTO orderUpdateDTO) throws OrderException;
    void cancelOrder(Long orderId, Long userId) throws OrderException;
    void updateOrderPaymentStatus(PaymentStatusUpdateEventDTO paymentStatus);

    String getAssignedRiderForOrder(@NotNull Long orderId);
}

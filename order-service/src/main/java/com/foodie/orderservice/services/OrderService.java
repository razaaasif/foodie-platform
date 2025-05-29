package com.foodie.orderservice.services;

import com.foodie.orderservice.constants.OrderStatus;
import com.foodie.orderservice.constants.PaymentStatus;
import com.foodie.orderservice.dto.OrderRequestDTO;
import com.foodie.orderservice.dto.OrderResponseDTO;
import com.foodie.orderservice.exception.OrderException;

import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequest, Long userId);
    OrderResponseDTO getOrderById(Long orderId, Long userId) throws OrderException;
    List<OrderResponseDTO> getOrdersByUser(Long userId);
    OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus status, Long userId) throws OrderException;
    void cancelOrder(Long orderId, Long userId) throws OrderException;
    void updateOrderPaymentStatus(Long orderId, PaymentStatus paymentStatus);
}

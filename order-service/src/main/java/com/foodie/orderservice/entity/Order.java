package com.foodie.orderservice.entity;

import com.foodie.orderservice.constants.OrderStatus;
import com.foodie.orderservice.constants.PaymentMethod;
import com.foodie.orderservice.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created on 28/05/25.
 *
 * @author : aasif.raza
 */
@Entity(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long restaurantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    @Column
    private LocalDateTime deliveryTime;
    @Column
    private LocalDateTime paymentTime;
    @Column(nullable = false)
    private String deliveryAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    @Column
    private String riderId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String transactionId;

    @Column
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedOn;
}
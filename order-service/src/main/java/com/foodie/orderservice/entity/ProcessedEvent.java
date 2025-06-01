package com.foodie.orderservice.entity;

import com.foodie.commons.constants.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created on 31/05/25.
 *
 * @author : aasif.raza
 */

@Entity
@Table(name = "processed_events", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"order_id", "event_type"})
})
@Getter
@Setter
@NoArgsConstructor
public class ProcessedEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus eventType;

    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt;

    public ProcessedEvent(Long orderId , OrderStatus orderStatus ){
        this.orderId = orderId;
        this.eventType = orderStatus;
        this.processedAt = LocalDateTime.now();
    }
}

package com.shop.model;

import com.shop.shared.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long shopCardId;

    @Column(nullable = false)
    private Long userId;

    private String receiverName;

    private String receiverPhone;

    private String receiverEmail;

    private Long postalCode;

    private OrderStatus status;

    @Column(length = 100000)
    private String address;

    @Column(length = 100000)
    private String description;

    @Column(nullable = false)
    private Long date;

    @PrePersist
    public void init() {
        this.status = OrderStatus.PAID;
        this.date = new Date().getTime();
    }

}

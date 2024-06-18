package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long shopCardId;

    private String receiverName;

    private String receiverPhone;

    private String receiverEmail;

    private Long postalCode;

    private Byte isActive;

    @Column(length = 100000)
    private String address;

    @Column(length = 100000)
    private String description;

    @PrePersist
    public void init() {
        this.isActive = 1;
    }

}

package com.shop.model;

import com.shop.shared.enums.ShopCardStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shopCard", uniqueConstraints = {@UniqueConstraint(columnNames = {"shopCardId"})})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShopCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopCardId;

    private Long userId;

    private Long orderId;

    private Long productId;

    private String size;

    private String color;

    private Long amount;

    private ShopCardStatus paid;

    @PrePersist
    private void init() {
        this.paid = ShopCardStatus.NOT_PAID;
    }
}

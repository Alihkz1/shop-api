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
@ToString
@Builder
public class ShopCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopCardId;

    private Long userId;

    private ShopCardStatus paid;

    @Column(length = 100000)
    private String products;

    @PrePersist
    private void init() {
        this.paid = ShopCardStatus.NOT_PAID;
    }
}

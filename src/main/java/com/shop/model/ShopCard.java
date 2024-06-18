package com.shop.model;

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

    private Byte paid;
    /* 0 not paid*/
    /* 1 paid*/

    @Column(length = 100000)
    private String products;

    @PrePersist
    private void init() {
        this.paid = 0;
    }
}

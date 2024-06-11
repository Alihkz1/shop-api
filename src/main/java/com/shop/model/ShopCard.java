package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shopCard", uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "shopCardId"})})
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

    @Column(length = 10000)
    private String products;
}

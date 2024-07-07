package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private Long categoryId;

    private Long price;

    private String title;

    private Long amount;

    private Long buyCount;

    private String imageUrl;

    @Column(length = 2000)
    private String description;

    @Column(length = 10000)
    private String size;

}

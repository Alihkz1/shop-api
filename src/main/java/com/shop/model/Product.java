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

    private Long likes;

    private Long buyCount;

    @Column(length = 10000000)
    private String imageUrl;

    private Byte primaryImageIndex;

    @Column(length = 2000)
    private String description;

    @PrePersist
    public void init() {
        this.buyCount = 0L;
        this.likes = 0L;
    }

}

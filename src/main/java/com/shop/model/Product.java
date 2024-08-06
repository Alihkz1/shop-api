package com.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonProperty("categoryId")
    public Long getCategoryId() {
        return category.getCategoryId();
    }

    private Long price;

    private String title;

    private Long amount;

    private Long likes;

    private Long buyCount;

    private Byte offPercent;

    @Column(length = 10000000)
    private String imageUrl;

    private Byte primaryImageIndex;

    @Column(length = 2000)
    private String description;

    @PrePersist
    public void init() {
        this.buyCount = 0L;
        this.likes = 0L;
        this.offPercent = 0;
    }
}

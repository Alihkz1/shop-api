package com.shop.model;

import com.shop.shared.enums.ShopCardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "productComment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Long userId;

    @Column(nullable = false)
    private Long productId;

    @Column(length = 10000)
    private String message;

    @Column(nullable = false)
    private Long date;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private ShopCardStatus state;

    @PrePersist
    public void init() {
        this.date = new Date().getTime();
        this.state = ShopCardStatus.NOT_PAID;
    }
}

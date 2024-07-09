package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shopCardProducts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShopCardProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cardId;

    private Long amount;

    private String size;
}

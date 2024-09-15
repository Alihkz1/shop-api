package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productColor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String color;
}

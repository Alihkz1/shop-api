package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productAbout")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductAbout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String key;

    private String value;
}

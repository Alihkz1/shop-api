package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productSize")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String size;

    private String amount;
}

package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "savedProduct")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SavedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long userId;
}

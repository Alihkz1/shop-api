package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "userProductSearch")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserProductSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(nullable = false)
    private String search;

    @Column(nullable = false)
    private Long date;

    @PrePersist
    private void init() {
        this.date = new Date().getTime();
    }

}

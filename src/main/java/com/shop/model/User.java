package com.shop.model;

import com.shop.shared.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long loginCount;

    private Long orderCount;

    private Long totalBuy;

    @PrePersist
    private void init() {
        this.role = Role.USER;
        this.loginCount = 0L;
        this.orderCount = 0L;
        this.totalBuy = 0L;
    }

    public User(Long userId) {
        this.userId = userId;
    }
}

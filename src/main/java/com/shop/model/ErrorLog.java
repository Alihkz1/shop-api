package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Table(name = "errorLog")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(length = 10000, nullable = false)
    private String message;

    @Column(nullable = false)
    private Long date;

    @Column(nullable = false)
    private Integer status;

    @PrePersist
    public void init() {
        this.date = new Date().getTime();
    }
}

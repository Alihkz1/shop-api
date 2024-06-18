package com.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long shopCardId;

    @Column(nullable = false)
    private Long userId;

    private String receiverName;

    private String receiverPhone;

    private String receiverEmail;

    private Long postalCode;

    private Byte status;
    /*
    * 0 -> just generated by user
    * 1 -> sent via post
    * 2 -> received by client
    * */

    @Column(length = 100000)
    private String address;

    @Column(length = 100000)
    private String description;

    @Column(nullable = false)
    private Long date;

    @PrePersist
    public void init() {
        this.status = 0;
        this.date = new Date().getTime();
    }

}

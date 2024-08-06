package com.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonProperty("userId")
    public Long getUserId() {
        return user.getUserId();
    }

    @Column(nullable = false)
    private String search;

    @Column(nullable = false)
    private Long date;

    @PrePersist
    private void init() {
        this.date = new Date().getTime();
    }

}

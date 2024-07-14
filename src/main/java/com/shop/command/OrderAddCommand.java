package com.shop.command;

import com.shop.model.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAddCommand {
    private Long userId;

    private String username;

    private String receiverName;

    private String receiverPhone;

    private String receiverEmail;

    private Long postalCode;

    private String address;

    private String description;

    public Order toEntity() {
        return Order.builder()
                .userId(userId)
                .username(username)
                .receiverName(receiverName)
                .receiverEmail(receiverEmail)
                .receiverPhone(receiverPhone)
                .postalCode(postalCode)
                .address(address)
                .description(description)
                .build();
    }
}

package com.shop.command;

import com.shop.model.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderAddCommand {
    private Long shopCardId;

    private Long userId;

    private String receiverName;

    private String receiverPhone;

    private String receiverEmail;

    private Long postalCode;

    private String address;

    private String description;

    public Order toEntity() {
        return Order.builder()
                .userId(userId)
                .shopCardId(shopCardId)
                .receiverName(receiverName)
                .receiverEmail(receiverEmail)
                .receiverPhone(receiverPhone)
                .postalCode(postalCode)
                .address(address)
                .description(description)
                .build();
    }
}

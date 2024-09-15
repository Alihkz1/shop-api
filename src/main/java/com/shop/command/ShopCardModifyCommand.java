package com.shop.command;

import com.shop.model.ShopCard;
import com.shop.shared.enums.ShopCardStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopCardModifyCommand {
    private Long shopCardId;
    private Long userId;
    private Long productId;
    private String size;
    private String color;
    private Long amount;
    private ShopCardStatus paid;

    public ShopCard toEntity() {
        return ShopCard.builder()
                .shopCardId(shopCardId)
                .userId(userId)
                .productId(productId)
                .size(size)
                .color(color)
                .amount(amount)
                .paid(paid)
                .build();
    }
}

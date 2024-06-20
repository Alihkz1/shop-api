package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopCardDto {
    private Long productId;

    private Long categoryId;

    private Long userId;

    private Long shopCardId;

    private Long price;

    private String title;

    private Long amount;

    private String imageUrl;

    private Long inCardAmount;
}

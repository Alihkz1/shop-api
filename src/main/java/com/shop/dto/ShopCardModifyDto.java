package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.model.ShopCard;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShopCardModifyDto {
    @JsonProperty("card")
    private ShopCard card;
}

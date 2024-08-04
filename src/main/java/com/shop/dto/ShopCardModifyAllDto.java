package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.model.ShopCard;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ShopCardModifyAllDto {
    @JsonProperty("cards")
    private List<ShopCard> cards;
}

package com.shop.dto;

import com.shop.model.ShopCard;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ShopCardLightListDto {
    private List<ShopCard> cards;
}

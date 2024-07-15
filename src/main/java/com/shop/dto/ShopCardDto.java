package com.shop.dto;

import com.shop.model.ShopCard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopCardDto {
    private ShopCard shopCard;
    private ProductDto product;
}

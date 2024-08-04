package com.shop.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SavedProductListDto {
    private List<ProductDto> products;
}

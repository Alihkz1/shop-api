package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProductAmountCheckListDto {
    @JsonProperty("products")
    private List<ProductAmountCheckDto> products;
}

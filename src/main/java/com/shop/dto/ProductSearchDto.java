package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.model.Product;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProductSearchDto {
    @JsonProperty("products")
    private List<Product> products;
}

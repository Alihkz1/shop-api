package com.shop.dto;

import com.shop.model.Product;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProductSearchDto {
    private List<Product> products;
}

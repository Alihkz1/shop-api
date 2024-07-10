package com.shop.dto;

import com.shop.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductDto {
    private Product product;
    private String size;
    private Long amount;
}

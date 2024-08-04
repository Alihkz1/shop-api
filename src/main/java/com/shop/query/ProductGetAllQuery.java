package com.shop.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductGetAllQuery {
    private Byte sort;
    private Long categoryId;
}

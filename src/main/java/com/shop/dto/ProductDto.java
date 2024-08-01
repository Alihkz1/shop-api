package com.shop.dto;

import com.shop.model.Product;
import com.shop.model.ProductAbout;
import com.shop.model.ProductSize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto {
    private Product product;
    private List<ProductSize> productSize;
    private List<ProductAbout> productAbout;
}

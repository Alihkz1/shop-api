package com.shop.dto;

import com.shop.model.ProductAbout;
import com.shop.model.ProductSize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRetrieveDto2 {
    ProductRetrieveDto product;
    List<ProductSize> productSize;
    List<ProductAbout> productAbout;
}

package com.shop.dto;

import com.shop.model.ProductAbout;
import com.shop.model.ProductColor;
import com.shop.model.ProductSize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRetrieveDto {
    ProductRetrieve product;
    List<ProductSize> productSize;
    List<ProductAbout> productAbout;
    List<ProductColor> productColor;
}

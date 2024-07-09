package com.shop.dto;

import com.shop.model.ProductSize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductAmountCheckDto {
    private Long productId;
    private Long amount;
    private Long price;
    private List<ProductSize> sizes;
}
        
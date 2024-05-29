package com.shop.command;

import com.shop.model.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductEditCommand {
    private Long productId;

    private Long categoryId;

    private Long price;

    private String title;

    private Long amount;

    private String imageUrl;

    public Product toEntity() {
        return Product.
                builder()
                .productId(productId)
                .price(price)
                .title(title)
                .amount(amount)
                .imageUrl(imageUrl)
                .build();
    }
}
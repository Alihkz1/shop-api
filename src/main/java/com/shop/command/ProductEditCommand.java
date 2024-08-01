package com.shop.command;

import com.shop.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEditCommand {
    private Long productId;

    private Long categoryId;

    private Long price;

    private String title;

    private Long amount;

    private String imageUrl;

    private Byte primaryImageIndex;

    private String description;

    private String size;

    private String about;

    public Product toEntity() {
        return Product.
                builder()
                .productId(productId)
                .price(price)
                .title(title)
                .amount(amount)
                .imageUrl(imageUrl)
                .primaryImageIndex(primaryImageIndex)
                .description(description)
                .build();
    }
}
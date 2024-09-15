package com.shop.command;

import com.shop.model.Category;
import com.shop.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddCommand {
    private Long categoryId;

    private Long price;

    private String title;

    private Long amount;

    private String imageUrl;

    private Byte primaryImageIndex;

    private String description;

    private String size;

    private String about;

    private String color;

    private Byte offPercent;

    public Product toEntity() {
        return Product.
                builder()
                .category(new Category(categoryId))
                .price(price)
                .title(title)
                .amount(amount)
                .offPercent(offPercent)
                .imageUrl(imageUrl)
                .primaryImageIndex(primaryImageIndex)
                .description(description)
                .build();
    }
}

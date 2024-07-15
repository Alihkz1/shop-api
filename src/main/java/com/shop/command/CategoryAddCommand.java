package com.shop.command;

import com.shop.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAddCommand {
    private String categoryName;

    private String imageUrl;

    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .imageUrl(imageUrl)
                .build();
    }
}

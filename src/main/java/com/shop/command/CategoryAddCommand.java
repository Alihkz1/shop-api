package com.shop.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.model.Category;
import lombok.Builder;
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

package com.shop.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.model.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryAddCommand {
    @JsonProperty("categoryName")
    private final String categoryName;

    @JsonProperty("imageUrl")
    private final String imageUrl;

    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .imageUrl(imageUrl)
                .build();
    }
}

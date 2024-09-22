package com.shop.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CategoryLightListClass implements Serializable {
    private Long categoryId;
    private String categoryName;
    private String imageUrl;
    private String productCount;

    public CategoryLightListClass(Long categoryId, String categoryName, String imageUrl, String productCount) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
        this.productCount = productCount;
    }
}

package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface CategoryLightList {
    @JsonProperty("categoryId")
    Long getCategoryId();

    @JsonProperty("categoryName")
    String getCategoryName();

    @JsonProperty("imageUrl")
    String getImageUrl();

    @JsonProperty("productCount")
    String getProductCount();
}

package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ProductRetrieve {
    @JsonProperty("productId")
    Long getProductId();

    @JsonProperty("categoryId")
    Long getCategoryId();

    @JsonProperty("price")
    Long getPrice();

    @JsonProperty("title")
    String getTitle();

    @JsonProperty("amount")
    Long getAmount();

    @JsonProperty("likes")
    Long getLikes();

    @JsonProperty("buyCount")
    Long getBuyCount();

    @JsonProperty("imageUrl")
    String getImageUrl();

    @JsonProperty("primaryImageIndex")
    Byte getPrimaryImageIndex();

    @JsonProperty("description")
    String getDescription();

    @JsonProperty("categoryName")
    String getCategoryName();

}

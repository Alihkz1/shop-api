package com.shop.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface CardProductIdAmount {
    @JsonProperty("productId")
    Long getProductId();

    @JsonProperty("price")
    Long getPrice();

    @JsonProperty("amount")
    Long getAmount();
}

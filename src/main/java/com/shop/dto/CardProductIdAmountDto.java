package com.shop.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface CardProductIdAmountDto {
    @JsonProperty("productId")
    Long getProductId();

    @JsonProperty("price")
    Long getPrice();

    @JsonProperty("amount")
    Long getAmount();
}

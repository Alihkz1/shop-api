package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductRetrieveFinalDto {
    @JsonProperty("product")
    private ProductRetrieveDto product;
}

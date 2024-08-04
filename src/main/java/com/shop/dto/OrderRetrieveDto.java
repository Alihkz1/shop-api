package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderRetrieveDto {
    @JsonProperty("order")
    private OrderDto order;
}

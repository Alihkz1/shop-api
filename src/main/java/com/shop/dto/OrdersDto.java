package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class OrdersDto {
    @JsonProperty("orders")
    private List<OrderDto> orders;
}

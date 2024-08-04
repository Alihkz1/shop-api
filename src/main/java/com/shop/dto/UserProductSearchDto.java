package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.model.UserProductSearch;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserProductSearchDto {
    @JsonProperty("history")
    private List<UserProductSearch> history;
}

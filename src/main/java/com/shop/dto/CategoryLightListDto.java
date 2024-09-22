package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public class CategoryLightListDto implements Serializable {
    @JsonProperty("categories")
    List<CategoryLightListClass> categories;
}


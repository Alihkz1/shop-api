package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CategoryListDto {
    private List<CategoryList> categories;
}

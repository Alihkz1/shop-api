package com.shop.dto;

import com.shop.model.UserProductSearch;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserProductSearchDto {
    private List<UserProductSearch> history;
}

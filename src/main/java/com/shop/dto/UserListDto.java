package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.model.User;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserListDto {
    @JsonProperty("users")
    private List<User> users;
}

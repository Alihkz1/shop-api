package com.shop.dto;

import com.shop.model.User;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserListDto {
    private List<User> users;
}

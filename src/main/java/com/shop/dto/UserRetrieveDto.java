package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRetrieveDto {
    @JsonProperty("user")
    private User user;
}

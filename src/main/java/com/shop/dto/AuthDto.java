package com.shop.dto;

import com.shop.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class AuthDto {
    private User user;
    private String token;
}

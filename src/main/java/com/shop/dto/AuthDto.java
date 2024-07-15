package com.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class AuthDto {
    private UserDto user;
    private String token;
}

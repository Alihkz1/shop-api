package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class AuthDto {
    @JsonProperty("user")
    private UserDto user;
    @JsonProperty("token")
    private String token;
}

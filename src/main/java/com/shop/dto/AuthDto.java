package com.shop.dto;

import com.shop.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@Builder
public class AuthDto {
    private User user;
    private String token;
}

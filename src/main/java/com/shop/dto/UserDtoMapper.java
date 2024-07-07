package com.shop.dto;

import com.shop.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getUserId(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getRole()
        );
    }
}

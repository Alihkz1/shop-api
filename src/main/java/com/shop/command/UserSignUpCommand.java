package com.shop.command;

import com.shop.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpCommand {
    private String name;

    private String phone;

    private String email;

    private String password;

    public User toEntity(String encodedPassword) {
        return User.builder()
                .name(name)
                .phone(phone)
                .email(email)
                .password(encodedPassword)
                .build();
    }
}

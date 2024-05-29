package com.shop.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSignUpCommand {
    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    public User toEntity() {
        return User.builder()
                .name(name)
                .phone(phone)
                .email(email)
                .password(password)
                .build();
    }


}

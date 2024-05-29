package com.shop.command;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserEditCommand {
    private Long userId;

    private String name;

    private String phone;

    private String email;

    private String password;
}

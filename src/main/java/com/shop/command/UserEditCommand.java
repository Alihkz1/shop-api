package com.shop.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditCommand {
    private Long userId;

    private String name;

    private String phone;

    private String email;

    private String password;
}

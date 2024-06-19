package com.shop.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginCommand {

    private String email;

    private String password;
}

package com.shop.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginCommand {

    private String email;

    private String password;
}

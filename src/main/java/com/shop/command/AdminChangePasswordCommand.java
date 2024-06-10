package com.shop.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminChangePasswordCommand {
    private Long userId;
    private String newPassword;
}

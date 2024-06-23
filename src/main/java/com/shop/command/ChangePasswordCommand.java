package com.shop.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordCommand {
    private Long userId;
    private String newPassword;
}

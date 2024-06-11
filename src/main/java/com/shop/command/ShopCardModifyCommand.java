package com.shop.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopCardModifyCommand {
    private Long userId;
    private String products;
}

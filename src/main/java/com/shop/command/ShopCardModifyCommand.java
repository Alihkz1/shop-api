package com.shop.command;

import com.shop.dto.ShopCardDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopCardModifyCommand {
    private Long userId;
    private List<ShopCardDto> products;
}

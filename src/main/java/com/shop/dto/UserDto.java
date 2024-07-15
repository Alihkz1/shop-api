package com.shop.dto;

import com.shop.shared.enums.Role;

public record UserDto(
        Long userId,
        String name,
        String phone,
        String email,
        Role role,
        Long loginCount,
        Long orderCount,
        Long totalBuy
) {
}

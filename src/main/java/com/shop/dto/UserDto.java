package com.shop.dto;

import com.shop.shared.enums.Role;
import lombok.Getter;
import lombok.Setter;


public record UserDto(
        Long userId,
        String name,
        String phone,
        String email,
        Role role,
        Long loginCount
) {
}

package com.shop.query;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderGetAllQuery {
    @NotNull
    private Long userId;
    @Nullable
    private Byte status;
}

package com.shop.query;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderGetAllQuery {
    @Nullable
    private Byte status;
}

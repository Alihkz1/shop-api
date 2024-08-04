package com.shop.query;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchQuery {
    @NotNull
    private String q; /* search query*/
    @Nullable
    private Long u; /* userId */
}

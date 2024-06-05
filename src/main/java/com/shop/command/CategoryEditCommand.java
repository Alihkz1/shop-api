package com.shop.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryEditCommand {
    private final String categoryName;

    private final Long categoryId;

    private final String imageUrl;
}

package com.shop.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryEditCommand {
    private String categoryName;

    private Long categoryId;

    private String imageUrl;
}

package com.shop.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryList {
    private Long categoryId;

    private String categoryName;

    private String imageUrl;

    private List<ProductDto> products;
}

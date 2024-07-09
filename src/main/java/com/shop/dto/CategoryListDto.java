package com.shop.dto;

import com.shop.model.Product;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListDto {
    private Long categoryId;

    private String categoryName;

    private String imageUrl;

    private List<ProductDto> products;
}

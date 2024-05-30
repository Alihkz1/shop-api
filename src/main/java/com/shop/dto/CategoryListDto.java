package com.shop.dto;

import com.shop.model.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListDto {
    private Long categoryId;

    private String categoryName;

    private String imageUrl;

    private List<Product> products;
}

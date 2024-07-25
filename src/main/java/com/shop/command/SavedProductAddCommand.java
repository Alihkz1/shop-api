package com.shop.command;

import com.shop.model.SavedProduct;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavedProductAddCommand {
    private Long userId;
    private Long productId;

    public SavedProduct toEntity() {
        return SavedProduct.builder()
                .userId(userId)
                .productId(productId)
                .build();
    }
}

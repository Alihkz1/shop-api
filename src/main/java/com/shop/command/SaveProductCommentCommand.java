package com.shop.command;

import com.shop.model.ProductComment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveProductCommentCommand {
    private Long userId;
    private Long productId;
    private String message;

    public ProductComment toEntity() {
        return ProductComment.builder()
                .userId(userId)
                .productId(productId)
                .message(message)
                .build();
    }
}

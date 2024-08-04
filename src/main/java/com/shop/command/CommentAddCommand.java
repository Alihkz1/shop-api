package com.shop.command;

import com.shop.model.Comment;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentAddCommand {
    private Long userId;
    private String message;

    public Comment toEntity(Long userId) {
        return Comment.builder()
                .message(message)
                .userId(userId)
                .read(false)
                .build();
    }
}

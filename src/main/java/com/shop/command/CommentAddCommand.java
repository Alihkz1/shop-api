package com.shop.command;

import com.shop.model.Comment;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentAddCommand {
    private Long userId;
    private String message;

    public Comment toEntity() {
        return Comment.builder()
                .userId(userId)
                .message(message)
                .read(false)
                .build();
    }
}

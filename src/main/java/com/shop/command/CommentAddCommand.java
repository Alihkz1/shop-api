package com.shop.command;

import com.shop.model.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CommentAddCommand {
    private final Long userId;
    private final String message;

    public Comment toEntity() {
        return Comment.builder()
                .userId(userId)
                .message(message)
                .read(false)
                .build();
    }
}

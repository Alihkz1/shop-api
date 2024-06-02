package com.shop.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentEditCommand {
    private Long commentId;
    private String message;
    private Boolean read;
}

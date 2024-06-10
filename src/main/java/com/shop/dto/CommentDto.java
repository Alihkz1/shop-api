package com.shop.dto;

import com.shop.model.Comment;
import com.shop.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class CommentDto {
    private Comment comment;

    private Optional<User> user;
}

package com.shop.dto;

import com.shop.model.Comment;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserCommentDto {
    private List<Comment> userComments;
}

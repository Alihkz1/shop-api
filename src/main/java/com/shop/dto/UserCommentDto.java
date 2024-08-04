package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.model.Comment;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserCommentDto {
    @JsonProperty("userComments")
    private List<Comment> userComments;
}

package com.shop.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryEditCommand {
    @JsonProperty("categoryName")
    private final String categoryName;

    @JsonProperty("id")
    private final Long categoryId;
}

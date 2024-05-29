package com.shop.shared;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Response<T> {
    private boolean success = true;
    private String message = "";
    private T data;

    public Response(T data) {
        this.data = data;
    }
}
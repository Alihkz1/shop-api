package com.shop.shared.classes;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Builder
public class Response<T> {
    private boolean success = true;
    private String message = "";
    private T data;

    public Response(T data) {
        this.data = data;
    }
}
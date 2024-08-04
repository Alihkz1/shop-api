package com.shop.shared.classes;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Builder
public class Response {
    private boolean success = true;
    private String message = "";
    private Object data;

    public Response(Object data) {
        this.data = data;
    }
}
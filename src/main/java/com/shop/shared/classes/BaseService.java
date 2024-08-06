package com.shop.shared.classes;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(force = true)
public class BaseService {

    protected ResponseEntity<Response> successResponse(Object data) {
        return new ResponseEntity<>(Response.builder().data(data).success(true).build(), HttpStatus.OK);
    }

    protected ResponseEntity<Response> successResponse() {
        return successResponse(null);
    }
}

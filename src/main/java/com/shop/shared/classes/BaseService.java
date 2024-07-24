package com.shop.shared.classes;

import org.springframework.http.ResponseEntity;

public class BaseService {

    protected ResponseEntity<Response> successResponse(Object data) {
        Response response = new Response();
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<Response> successResponse() {
        return successResponse(null);
    }

    public ResponseEntity<Response> errorResponse(String message) {
        Response response = new Response();
        response.setSuccess(false);
        response.setMessage(message);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> errorResponse() {
        return errorResponse(null);
    }

}

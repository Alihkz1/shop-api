package com.shop.shared.classes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseService {

    protected ResponseEntity<Response> successResponse(Object data) {
        Response response = new Response();
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected ResponseEntity<Response> successResponse() {
        return successResponse(null);
    }

    public ResponseEntity<Response> errorResponse(String message, HttpStatus status) {
        Response response = new Response();
        response.setSuccess(false);
        response.setMessage(message);
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<Response> badRequestResponse(String message) {
        Response response = new Response();
        response.setSuccess(false);
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<Response> serverErrorResponse(String message) {
        Response response = new Response();
        response.setSuccess(false);
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

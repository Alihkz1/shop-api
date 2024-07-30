package com.shop.shared.classes;

import com.shop.model.ErrorLog;
import com.shop.repository.ErrorLogRepository;
import com.shop.shared.enums.ErrorMessagesEnum;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(force = true)
public class BaseService {

    @Autowired
    private final ErrorLogRepository errorLogRepository;

    protected ResponseEntity<Response> successResponse(Object data) {
        Response response = new Response();
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected ResponseEntity<Response> successResponse() {
        return successResponse(null);
    }

    public ResponseEntity<Response> errorResponse(String message, HttpStatus status) {
        logError(message, Integer.valueOf(String.valueOf(status)));
        Response response = new Response();
        response.setSuccess(false);
        response.setMessage(message);
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<Response> badRequestResponse(ErrorMessagesEnum errorEnum) {
        logError(errorEnum.getMessage(), 400);
        Response response = new Response();
        response.setSuccess(false);
        response.setMessage(errorEnum.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<Response> serverErrorResponse(String message) {
        logError(message, 500);
        Response response = new Response();
        response.setSuccess(false);
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logError(String message, Integer status) {
        ErrorLog errorLog = ErrorLog.builder()
                .message(message)
                .status(status)
                .build();
        errorLogRepository.save(errorLog);
    }
}

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
        return new ResponseEntity<>(Response.builder().data(data).success(true).build(), HttpStatus.OK);
    }

    protected ResponseEntity<Response> successResponse() {
        return successResponse(null);
    }

    public ResponseEntity<Response> errorResponse(String message, HttpStatus status) {
        logError(message, Integer.valueOf(String.valueOf(status)));
        return new ResponseEntity<>(
                Response.builder().success(false).message(message).build(),
                status
        );
    }

    public ResponseEntity<Response> badRequestResponse(ErrorMessagesEnum errorEnum) {
        logError(errorEnum.getMessage(), 400);
        return new ResponseEntity<>(
                Response.builder().success(false).message(errorEnum.getMessage()).build(),
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<Response> serverErrorResponse(String message) {
        logError(message, 500);
        return new ResponseEntity<>(
                Response.builder().success(false).message(message).build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private void logError(String message, Integer status) {
        ErrorLog errorLog = ErrorLog.builder()
                .message(message)
                .status(status)
                .build();
        assert errorLogRepository != null;
        errorLogRepository.save(errorLog);
    }
}

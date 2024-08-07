package com.shop.config;

import com.shop.model.ErrorLog;
import com.shop.repository.ErrorLogRepository;
import com.shop.shared.Exceptions.BadRequestException;
import com.shop.shared.classes.Response;
import com.shop.shared.classes.UserThread;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorLogRepository errorLogRepository;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(HttpServletRequest request, Exception ex) {
        String url = request.getRequestURL().toString();
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        logError(url, message, status.value());
        return new ResponseEntity<>(
                Response.builder().success(false).message(message).build(),
                status
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleBadRequestException(HttpServletRequest request, BadRequestException ex) {
        String url = request.getRequestURL().toString();
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        logError(url, message, status.value());
        return new ResponseEntity<>(
                Response.builder().success(false).message(message).build(),
                status
        );
    }

    private void logError(String requestUrl, String message, Integer status) {
        ErrorLog errorLog = ErrorLog.builder()
                .url(requestUrl)
                .message(message)
                .status(status)
                .userId(UserThread.getUserId())
                .build();
        errorLogRepository.save(errorLog);
    }
}

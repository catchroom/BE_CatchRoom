package com.example.catchroom_be.global.exception;

import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.user.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleBaseException(BaseException e) {
        return ResponseEntity.badRequest().body(
                ApiResponse.create(e.getCode(), ErrorMessage.createErrorMessage(e.getMessage()))
        );
    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleUserException(BaseException e) {
        return ResponseEntity.badRequest().body(
                ApiResponse.create(e.getCode(), ErrorMessage.createErrorMessage(e.getMessage()))
        );
    }
}

package com.example.catchroom_be.global.exception;

import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.domain.user.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleBaseException(BaseException e) {
        return ResponseEntity.badRequest().body(
                ApiResponse.create(e.getCode(), ErrorMessage.createErrorMessage(e.getMessage()))
        );
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleUserException(UserException e) {
        return ResponseEntity.badRequest().body(
                ApiResponse.create(e.getCode(), ErrorMessage.createErrorMessage(e.getMessage()))
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorMessage>> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                ErrorCode errorCode;
                if ("유효한 이메일 형식이 아닙니다.".equals(error.getDefaultMessage())) {
                    errorCode = ErrorCode.USER_EMAIL_NOT_VALID;
                    return ResponseEntity.badRequest().body(
                            ApiResponse.create(errorCode.getCode(), ErrorMessage.createErrorMessage(errorCode.getMessage()))
                    );
                } else if ("이메일은 비어있을 수 없습니다.".equals(error.getDefaultMessage())) {
                    errorCode = ErrorCode.USER_EMAIL_NOT_VALID;
                    return ResponseEntity.badRequest().body(
                            ApiResponse.create(errorCode.getCode(), ErrorMessage.createErrorMessage(errorCode.getMessage()))
                    );
                } else if ("비밀번호는 비어있을 수 없습니다.".equals(error.getDefaultMessage())) {
                    errorCode = ErrorCode.USER_PASSWORD_NOT_VALID;
                    return ResponseEntity.badRequest().body(
                            ApiResponse.create(errorCode.getCode(), ErrorMessage.createErrorMessage(errorCode.getMessage()))
                    );
                } else if ("비밀번호 형식이 유효하지 않습니다.".equals(error.getDefaultMessage())) {
                    errorCode = ErrorCode.USER_PASSWORD_NOT_VALID;
                    return ResponseEntity.badRequest().body(
                            ApiResponse.create(errorCode.getCode(), ErrorMessage.createErrorMessage(errorCode.getMessage()))
                    );
                } else if ("전화번호는 비어있을 수 없습니다.".equals(error.getDefaultMessage())) {
                    errorCode = ErrorCode.USER_PHONE_NOT_VALID;
                    return ResponseEntity.badRequest().body(
                            ApiResponse.create(errorCode.getCode(), ErrorMessage.createErrorMessage(errorCode.getMessage()))
                    );
                } else if ("전화번호 형식이 올바르지 않습니다.".equals(error.getDefaultMessage())) {
                    errorCode = ErrorCode.USER_PHONE_NOT_VALID;
                    return ResponseEntity.badRequest().body(
                            ApiResponse.create(errorCode.getCode(), ErrorMessage.createErrorMessage(errorCode.getMessage()))
                    );
                } else if ("닉네임은 비어있을 수 없습니다.".equals(error.getDefaultMessage())) {
                    errorCode = ErrorCode.USER_NICKNAME_NOT_VALID;
                    return ResponseEntity.badRequest().body(
                            ApiResponse.create(errorCode.getCode(), ErrorMessage.createErrorMessage(errorCode.getMessage()))
                    );
                } else if ("닉네임 형식이 올바르지 않습니다.".equals(error.getDefaultMessage())) {
                    errorCode = ErrorCode.USER_NICKNAME_NOT_VALID;
                    return ResponseEntity.badRequest().body(
                            ApiResponse.create(errorCode.getCode(), ErrorMessage.createErrorMessage(errorCode.getMessage()))
                    );
                } else if ("이름은 비어있을 수 없습니다.".equals(error.getDefaultMessage())) {
                    errorCode = ErrorCode.USER_NAME_NOT_VALID;
                    return ResponseEntity.badRequest().body(
                            ApiResponse.create(errorCode.getCode(), ErrorMessage.createErrorMessage(errorCode.getMessage()))
                    );
                } else if ("이름 형식이 올바르지 않습니다.".equals(error.getDefaultMessage())) {
                    errorCode = ErrorCode.USER_NAME_NOT_VALID;
                    return ResponseEntity.badRequest().body(
                            ApiResponse.create(errorCode.getCode(), ErrorMessage.createErrorMessage(errorCode.getMessage()))
                    );
                } else {
                    errorCode = ErrorCode.SERVER_ERROR;
                    return ResponseEntity.badRequest().body(
                            ApiResponse.create(errorCode.getCode(), ErrorMessage.createErrorMessage(errorCode.getMessage()))
                    );
                }

            }

        }
        return ResponseEntity.badRequest().body(
                ApiResponse.create(ErrorCode.SERVER_ERROR.getCode(),
                        ErrorMessage.createErrorMessage(ErrorCode.SERVER_ERROR.getMessage()))
        );
    }

}

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
    public void handleValidException(MethodArgumentNotValidException e) {
        System.out.println("1");
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                if ("유효한 이메일 형식이 아닙니다.".equals(error.getDefaultMessage())) {
                    throw new UserException(ErrorCode.USER_EMAIL_NOT_VALID);
                } else if ("이메일은 비어있을 수 없습니다.".equals(error.getDefaultMessage())) {
                    throw new UserException(ErrorCode.USER_EMAIL_NOT_VALID);
                } else if ("비밀번호는 비어있을 수 없습니다.".equals(error.getDefaultMessage())) {
                    throw new UserException(ErrorCode.USER_PASSWORD_NOT_VALID);
                } else if ("비밀번호 형식이 유효하지 않습니다.".equals(error.getDefaultMessage())) {
                    throw new UserException(ErrorCode.USER_PASSWORD_NOT_VALID);
                } else if ("전화번호는 비어있을 수 없습니다.".equals(error.getDefaultMessage())) {
                    throw new UserException(ErrorCode.USER_PHONE_NOT_VALID);
                }else if ("전화번호 형식이 올바르지 않습니다.".equals(error.getDefaultMessage())) {
                    throw new UserException(ErrorCode.USER_PHONE_NOT_VALID);
                } else if ("닉네임은 비어있을 수 없습니다.".equals(error.getDefaultMessage())) {
                    throw new UserException(ErrorCode.USER_NICKNAME_NOT_VALID);
                } else if ("닉네임 형식이 올바르지 않습니다.".equals(error.getDefaultMessage())) {
                    throw new UserException(ErrorCode.USER_NICKNAME_NOT_VALID);
                } else if ("이름은 비어있을 수 없습니다.".equals(error.getDefaultMessage())) {
                    throw new UserException(ErrorCode.USER_NAME_NOT_VALID);
                } else if ("이름 형식이 올바르지 않습니다.".equals(error.getDefaultMessage())) {
                    throw new UserException(ErrorCode.USER_NAME_NOT_VALID);
                }else {
                    throw new UserException(ErrorCode.SERVER_ERROR);
                }

            }

        }
    }
}

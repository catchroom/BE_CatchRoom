package com.example.catchroom_be.global.exception;
import org.springframework.security.core.AuthenticationException;


public class CustomAuthenticationException extends AuthenticationException {
    private final int errorCode;

    public CustomAuthenticationException(String msg, int errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

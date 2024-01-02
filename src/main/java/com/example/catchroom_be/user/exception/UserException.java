package com.example.catchroom_be.user.exception;

import com.example.catchroom_be.global.exception.BaseException;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends BaseException {
    private final ErrorCode errorCode;

    public UserException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
    public Integer getCode() {
        return errorCode.getCode();
    }

}

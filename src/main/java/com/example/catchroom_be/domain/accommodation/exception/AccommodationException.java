package com.example.catchroom_be.domain.accommodation.exception;

import com.example.catchroom_be.global.exception.BaseException;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class AccommodationException extends BaseException {
    private final ErrorCode errorCode;

    public AccommodationException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public Integer getCode() {
        return errorCode.getCode();
    }
}

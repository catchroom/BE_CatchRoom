package com.example.catchroom_be.domain.chatroom.exception;

import com.example.catchroom_be.global.exception.BaseException;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ChatRoomException extends BaseException {
    private final ErrorCode errorCode;

    public ChatRoomException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
    public Integer getCode() {
        return errorCode.getCode();
    }
}

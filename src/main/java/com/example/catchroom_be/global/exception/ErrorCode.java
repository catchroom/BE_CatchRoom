package com.example.catchroom_be.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //회원
    USER_EMAIL_NOT_VALID(1001, "이메일 형식이 올바르지 않습니다."),
    USER_PASSWORD_NOT_VALID(1002,"비밀번호 형식이 올바르지 않습니다."),
    USER_PHONE_NOT_VALID(1003,"전화번호 형식이 올바르지 않습니다."),
    USER_NICKNAME_NOT_VALID(1004,"닉네임 형식이 올바르지 않습니다."),
    USER_NAME_NOT_VALID(1008,"닉네임 형식이 올바르지 않습니다."),
    USER_CANNOT_LOGIN(1007,"회원 아이디 또는 비밀번호가 올바르지 않습니다"),
    USER_NICKNAME_DUPLICATE(1011,"닉네임이 중복되었습니다."),
    USER_EMAIL_DUPLICATE(1005,"이메일이 중복되었습니다."),


    //기본 에러
    INVALID_ACCESS_TOKEN(5000,"유효하지 않은 엑세스 토큰입니다."),
    EXPIRE_ACCESS_TOKEN(5001,"액세스 토큰이 만료되었습니다."),
    SERVER_ERROR(5002,"서비스 오류입니다."),
    INVALID_DATA_ERROR(5003,"데이터 형식이 올바르지 않습니다."),


    ;

    private final Integer code;
    private final String message;

}

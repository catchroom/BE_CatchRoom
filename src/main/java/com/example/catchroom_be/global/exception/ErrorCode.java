package com.example.catchroom_be.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //회원
    MEMBER_NOT_FOUND(1001, "회원 아이디가 존재하지 않습니다."),
    KAKAO_ACCESS_TOKEN_NOT_FOUND(1002,"카카오 엑세스 토큰 발급이 실패하였습니다.")
    ;

    private final Integer code;
    private final String message;

}

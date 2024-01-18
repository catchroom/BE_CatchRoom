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
    USER_NICKNAME_NOT_DUPLICATE(1010,"닉네임이 중복되지 않았습니다."),
    USER_EMAIL_NOT_DUPLICATE(1012,"이메일이 중복되지 않았습니다."),
    USER_REFRESHTOKEN_MISSING(1014,"refresh token이 비어있습니다."),
    USER_REFRESHTOKEN_EXPIRE(1015,"refresh token이 만료되었습니다."),
    USER_NOT_FOUND(1016,"등록된 회원정보가 없습니다."),
    //마이페이지
    MYPAGE_LOGOUT_ERROR(2001,"로그아웃에 실패하였습니다."),
    MYPAGE_PROFILE_REFACT_ERROR(2003,"프로필 수정에 실패하였습니다."),



    //예치금 통장 관련
    ACCOUNT_NOT_SAVED(1099, "계좌가 올바르게 저장되지 않았습니다."),



    //객실 & 숙박
    ROOM_NOT_FOUND(4041, "유효하지 않는 객실 정보입니다."),
    ACCOMMODATION_NOT_FOUND(4042, "유효하지 않는 숙박 정보입니다."),

    //판매 관련
    PRODUCT_NOT_FOUND(4043, "등록된 상품이 없습니다."),
    INVALID_PRODUCT_OWNER(4022, "본인의 숙박권/상품만 수정할 수 있습니다."),
    INVALID_REGIST_TIME(4011, "판매 마감 날짜는 현시점 이후 ~ 체크인 날짜 사이 값으로 설정해야합니다."),
    DUPLICATED_REGIST_PRODUCT(4012,"이미 등록된 상품입니다."),
    //야놀자 데이터 관련
    ORDERHISTROY_NOT_FOUND(4044, "구매하신 숙박권이 없습니다."),

    //채팅 관련
    CHATROOM_USER_NOT_FOUND(6997, "접근할 수 없는 유저입니다."),
    CHATROOM_NOT_FOUND(6998, "유효하지 않는 채팅방 입니다."),
    CHATROOM_PARTNER_USER_NOT_FOUND(6999, "채팅방 상대방 유저 정보가 유효하지 않습니다."),


    //기본 에러
    INVALID_ACCESS_TOKEN(5000,"유효하지 않은 엑세스 토큰입니다."),
    EXPIRE_ACCESS_TOKEN(5001,"액세스 토큰이 만료되었습니다."),
    SERVER_ERROR(5002,"서비스 오류입니다."),
    INVALID_DATA_ERROR(5003,"데이터 형식이 올바르지 않습니다."),


    ;

    private final Integer code;
    private final String message;

}

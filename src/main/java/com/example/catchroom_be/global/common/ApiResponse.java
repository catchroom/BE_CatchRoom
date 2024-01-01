package com.example.catchroom_be.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final Integer code;        // 사용자 정의 코드
    private final T data;           // API에서 반환되는 데이터

    public static <T> ApiResponse<T> create(int code, T data) {
        return new ApiResponse<>(code, data);
    }
}
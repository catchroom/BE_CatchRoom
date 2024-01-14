package com.example.catchroom_be.domain.user.dto.request;

public class TokenRequest {
    String authCode;

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCode() {
        return authCode;
    }
}

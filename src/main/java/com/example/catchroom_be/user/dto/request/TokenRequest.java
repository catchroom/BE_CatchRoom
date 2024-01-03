package com.example.catchroom_be.user.dto.request;

public class TokenRequest {
    String authCode;

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthCode() {
        return authCode;
    }
}

package com.example.catchroom_be.user.dto.response;

import lombok.Getter;

@Getter
public class TokenResponse {
    private String tokenType;
    private String accessToken;
    private String idToken;
    private Integer expiresIn;
    private String refreshToken;
    private Integer refreshTokenExpiresIn;
    private String scope;
}

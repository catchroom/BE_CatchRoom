package com.example.catchroom_be.user.service;

import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.user.dto.response.TokenResponse;
import com.example.catchroom_be.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class KaKaoOAuthService {

    @Value("${KAKAO_CLIENT_ID}")
    private String clientId;

    @Value("${KAKAO_CLIENT_SECRET}")
    private String clientSecret;

    @Value("${KAKAO_LOGIN_REDIRECT_URL}")
    private String redirectUri;

    private static final String TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
    private TokenResponse tokenResponse;

    private RestTemplate restTemplate;

    public TokenResponse requestAccessToken(String code) throws UserException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(
                    TOKEN_REQUEST_URL,
                    HttpMethod.POST,
                    requestEntity,
                    TokenResponse.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                TokenResponse tokenResponse = responseEntity.getBody();

                if (tokenResponse != null && tokenResponse.getAccessToken() != null) {
                    return tokenResponse;
                } else {
                    throw new UserException(ErrorCode.KAKAO_ACCESS_TOKEN_NOT_FOUND);
                }
            } else {
                throw new UserException(ErrorCode.KAKAO_ACCESS_TOKEN_NOT_FOUND);
            }
        } catch (HttpClientErrorException e) {
            throw new UserException(ErrorCode.KAKAO_ACCESS_TOKEN_NOT_FOUND);
        }
    }
}

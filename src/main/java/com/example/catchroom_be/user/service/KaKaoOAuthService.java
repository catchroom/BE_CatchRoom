package com.example.catchroom_be.user.service;

import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.user.dto.response.KaKaoClientResponse;
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

import java.nio.charset.Charset;


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
    private static final String KAKAO_ID_GET_URL = "https://kapi.kakao.com/v2/user/me";
    private TokenResponse tokenResponse;

    private RestTemplate restTemplate;

    public KaKaoClientResponse requestAccessToken(String code) throws UserException {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8"));
        headers.setContentType(mediaType);

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
                    return getKaKaoId(tokenResponse.getAccessToken());
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

    public KaKaoClientResponse getKaKaoId(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8"));
        headers.setContentType(mediaType);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();


        ResponseEntity<KaKaoClientResponse> response = restTemplate.exchange(
                KAKAO_ID_GET_URL, HttpMethod.GET, entity, KaKaoClientResponse.class);

        return response.getBody();

    }
}

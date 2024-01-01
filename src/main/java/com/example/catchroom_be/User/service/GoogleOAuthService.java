package com.example.catchroom_be.User.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleOAuthService {
    @Value("${GOOGLE_CLIENT_ID}")
    private String CLIENT_ID;
    @Value("${GOOGLE_CLIENT_SECRET}")
    private String CLIENT_SECRET;
    @Value("${GOOGLE_LOGIN_REDIRECT_URL}")
    private String REDIRECT_URI;
    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

    public ResponseEntity<String> getAccessToken(String authCode) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> params = new HashMap<>();

        params.put("code",authCode);
        params.put("client_id",CLIENT_ID);
        params.put("client_secret",CLIENT_SECRET);
        params.put("redirect_uri",REDIRECT_URI);
        params.put("grant_type","authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL,params,String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }
        return null;
    }


}
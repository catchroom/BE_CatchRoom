package com.example.catchroom_be.User.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    private RestTemplate restTemplate;

    public String processOAuth(String authCode) {
        String clientId = "your-client-id";
        String clientSecret = "your-client-secret";
        String redirectUri = "your-redirect-uri";

        // 토큰 요청을 위한 URL 생성
        String tokenRequestUrl = "https://oauth2.googleapis.com/token"
                + "?client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&redirect_uri=" + redirectUri
                + "&grant_type=authorization_code"
                + "&code=" + authCode;

        // 토큰 요청
        String accessToken = restTemplate.postForObject(tokenRequestUrl, HttpMethod.POST, String.class);

        // 사용자 정보 요청
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange("https://www.googleapis.com/oauth2/v1/userinfo", HttpMethod.GET, entity, String.class);


        return response.getBody();
    }
}

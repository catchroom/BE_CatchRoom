package com.example.catchroom_be.User.controller;

import com.example.catchroom_be.User.dto.OauthAuthCodeRequest;
import com.example.catchroom_be.User.service.GoogleOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final GoogleOAuthService googleOAuthService;

    //  @PostMapping("/oauth2/callback")
    //  public String getAuthCode(@RequestBody OauthAuthCodeRequest oauthAuthCodeRequest) {
    //   return oauthAuthCodeRequest.getAuthCode();
    //  }

    @PostMapping("/oauth2/callback")
    public ResponseEntity<String> getAuthCode(@RequestBody OauthAuthCodeRequest oauthAuthCodeRequest) {
        return googleOAuthService.getAccessToken(oauthAuthCodeRequest.getAuthCode());

    }
}


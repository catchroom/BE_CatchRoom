package com.example.catchroom_be.User.controller;

import com.example.catchroom_be.User.dto.OauthAuthCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    @PostMapping("/oauth2/callback")
    public String getAuthCode(@RequestBody OauthAuthCodeRequest oauthAuthCodeRequest) {
        return oauthAuthCodeRequest.getAuthCode();
    }
}


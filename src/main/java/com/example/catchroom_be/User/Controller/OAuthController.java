package com.example.catchroom_be.User.Controller;

import com.example.catchroom_be.User.Service.GoogleOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {
    private final GoogleOAuthService googleOAuthService;

    @PostMapping("/oauth2/callback")
    public String getAuthCode(@RequestBody String authCode) {
        return googleOAuthService.getAccessToken(authCode);
    }
}


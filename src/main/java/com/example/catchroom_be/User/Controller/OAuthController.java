package com.example.catchroom_be.User.Controller;

import com.example.catchroom_be.User.Service.GoogleOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OAuthController {
    private final GoogleOAuthService googleOAuthService;

    @PostMapping("/oauth2/callback")
    public ResponseEntity<String> getAuthCode(@RequestParam String authCode) {
        return googleOAuthService.getAccessToken(authCode);
    }
}


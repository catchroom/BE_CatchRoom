package com.example.catchroom_be.user.controller;


import com.example.catchroom_be.global.exception.ErrorMessage;
import com.example.catchroom_be.global.exception.ResponseMessage;
import com.example.catchroom_be.global.exception.SuccessMessage;
import com.example.catchroom_be.user.dto.request.TokenRequest;
import com.example.catchroom_be.user.dto.response.TokenResponse;
import com.example.catchroom_be.user.service.KaKaoOAuthService;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private KaKaoOAuthService kaKaoOAuthService;

      @PostMapping("/oauth2/callback")
      public ResponseEntity<ApiResponse<TokenResponse>> getAuthCode(@RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = kaKaoOAuthService.requestAccessToken(tokenRequest.getAuthCode());
       return ResponseEntity.ok(ApiResponse.create(1000, tokenResponse));
      }

}


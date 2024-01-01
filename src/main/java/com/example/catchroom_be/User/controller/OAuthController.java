package com.example.catchroom_be.User.controller;

import com.example.catchroom_be.User.dto.OauthAuthCodeRequest;
import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.exception.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OAuthController {

      @PostMapping("/oauth2/callback")
      public ResponseEntity<ApiResponse<SuccessMessage>> getAuthCode(@RequestBody OauthAuthCodeRequest oauthAuthCodeRequest) {
       return ResponseEntity.ok(ApiResponse.create(1000, SuccessMessage.createSuccessMessage("OAuth accessToken 발급 성공")));
      }


}


package com.example.catchroom_be.user.controller;


import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.global.exception.SuccessMessage;
import com.example.catchroom_be.user.dto.request.TestRequest;
import com.example.catchroom_be.user.dto.request.TokenRequest;
import com.example.catchroom_be.user.exception.UserException;
import com.example.catchroom_be.user.service.KaKaoGetIdService;
import com.example.catchroom_be.user.service.KaKaoOAuthService;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OAuthController {
    private final KaKaoOAuthService kaKaoOAuthService;
    private final KaKaoGetIdService kaKaoGetIdService;

      @GetMapping("/oauth2/callback")
      public ResponseEntity<ApiResponse<Long>> getAuthCode(@RequestParam("code") String code) {

        String kakaoAccessToken = kaKaoOAuthService.requestAccessToken(code);

        if (kakaoAccessToken == null) {
            throw new UserException(ErrorCode.KAKAO_ACCESS_TOKEN_NOT_FOUND);
        }

        Long kakaoId = kaKaoGetIdService.getKaKaoId(kakaoAccessToken);


       return ResponseEntity.ok(ApiResponse.create(1000, kakaoId));
      }

      @PostMapping("/oauth2/test")
    public ResponseEntity<ApiResponse<Long>> getTestCode(@RequestBody TestRequest testRequest) {
          Long kakaoId = kaKaoGetIdService.getKaKaoId(testRequest.getAccessToken());

          return ResponseEntity.ok(ApiResponse.create(1000, kakaoId));

      }

}


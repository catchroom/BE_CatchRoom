package com.example.catchroom_be.domain.user.controller.user;

import com.example.catchroom_be.domain.user.dto.request.LoginRequest;
import com.example.catchroom_be.domain.user.dto.response.LoginResponse;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.service.me.*;
import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.jwt.dto.JwtPayload;
import com.example.catchroom_be.global.exception.CustomAuthenticationException;
import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.global.exception.SuccessMessage;
import com.example.catchroom_be.domain.user.dto.request.RegisterRequest;
import com.example.catchroom_be.global.jwt.service.MeJWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class MeController {


    private final MeRegisterService meRegisterService;
    private final MeLoginService meLoginService;
    private final MeNickNameService meNickNameService;
    private final MeEmailService meEmailService;
    private final MeAccessTokenService meAccessTokenService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SuccessMessage>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        meRegisterService.registerUser(registerRequest);
        return ResponseEntity.ok(ApiResponse.create(1000,SuccessMessage.createSuccessMessage("회원가입이 성공적으로 완료되었습니다.")));

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse = meLoginService.loginUser(loginRequest,response);
        return ResponseEntity.ok(ApiResponse.create(1006,loginResponse));

    }

    @GetMapping("/nickname/check")
    public ResponseEntity<ApiResponse<SuccessMessage>> nicknameCheck(@Valid @RequestParam String nickname) {
        meNickNameService.nicknameCheckUser(nickname);
        return ResponseEntity.ok(ApiResponse.create(1010,SuccessMessage.createSuccessMessage("닉네임이 중복되지 않았습니다.")));
    }

    @GetMapping("/email/check")
    public ResponseEntity<ApiResponse<SuccessMessage>> emailCheck(@Valid @RequestParam String email) {
        meEmailService.emailCheckUser(email);
        return ResponseEntity.ok(ApiResponse.create(1012,SuccessMessage.createSuccessMessage("이메일이 중복되지 않았습니다.")));
    }


    @PostMapping("/accesstoken")
    public ResponseEntity<ApiResponse<String>> accessToken(HttpServletRequest request,@AuthenticationPrincipal User user,HttpServletResponse response) {
        String newAccessToken = meAccessTokenService.accessTokenService(request,user,response);
        return ResponseEntity.ok(ApiResponse.create(1013,newAccessToken));
    }


}

package com.example.catchroom_be.domain.user.controller;

import com.example.catchroom_be.domain.user.dto.request.LoginRequest;
import com.example.catchroom_be.domain.user.dto.response.LoginResponse;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.service.me.*;
import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.config.JwtPayload;
import com.example.catchroom_be.global.exception.CustomAuthenticationException;
import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.global.exception.SuccessMessage;
import com.example.catchroom_be.domain.user.dto.request.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.security.sasl.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class MeController {


    private final MeRegisterService meRegisterService;
    private final MeLoginService meLoginService;
    private final MeNickNameService meNickNameService;
    private final MeEmailService meEmailService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MeJWTService meJWTService;




    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SuccessMessage>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        meRegisterService.registerUser(registerRequest);
        return ResponseEntity.ok(ApiResponse.create(1000,SuccessMessage.createSuccessMessage("회원가입이 성공적으로 완료되었습니다.")));

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = meLoginService.loginUser(loginRequest);
        return ResponseEntity.ok(ApiResponse.create(1006,loginResponse));

    }

    @GetMapping("/nickname/check")
    public ResponseEntity<ApiResponse<SuccessMessage>> nicknameCheck(@Valid @RequestParam String nickname) {
        meNickNameService.nicknameCheckUser(nickname);
        return ResponseEntity.ok(ApiResponse.create(1011,SuccessMessage.createSuccessMessage("닉네임이 중복되었습니다.")));
    }

    @GetMapping("/email/check")
    public ResponseEntity<ApiResponse<SuccessMessage>> emailCheck(@Valid @RequestParam String email) {
        meEmailService.emailCheckUser(email);
        return ResponseEntity.ok(ApiResponse.create(1005,SuccessMessage.createSuccessMessage("이메일이 중복되었습니다.")));
    }


    @PostMapping("/accesstoken")
    public ResponseEntity<ApiResponse<String>> accessToken(HttpServletRequest request,@AuthenticationPrincipal User user) {

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);


        //header에 refresh token이 있는지 확인
        if (bearerToken == null || !bearerToken.startsWith("Bearer")) {
            throw new UserException(ErrorCode.USER_ACCESSTOKEN_MISSING);
        }


        String refreshToken = bearerToken.substring(7);


        //redis에 있는 지 확인
        Boolean tokenExists = redisTemplate.hasKey(String.valueOf(user.getId()));

        if (tokenExists == null || !tokenExists) {
            throw new UserException(ErrorCode.USER_ACCESSTOKEN_MISSING);
        }


        try {
            // Refresh Token을 검증
            JwtPayload jwtPayload = meJWTService.verifyToken(refreshToken);

            // 새로운 Access Token을 생성
            String newAccessToken = meJWTService.createAccessToken(jwtPayload);


            // 새 Access Token을 클라이언트에게 반환
            return ResponseEntity.ok(ApiResponse.create(1013, newAccessToken));

        }  catch (CustomAuthenticationException e) {
            throw new UserException(ErrorCode.SERVER_ERROR);
        }


    }

}

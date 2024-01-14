package com.example.catchroom_be.domain.user.controller;

import com.example.catchroom_be.domain.user.dto.request.LoginRequest;
import com.example.catchroom_be.domain.user.dto.response.LoginResponse;
import com.example.catchroom_be.domain.user.service.me.MeLoginService;
import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.exception.SuccessMessage;
import com.example.catchroom_be.domain.user.dto.request.RegisterRequest;
import com.example.catchroom_be.domain.user.service.me.MeEmailService;
import com.example.catchroom_be.domain.user.service.me.MeNickNameService;
import com.example.catchroom_be.domain.user.service.me.MeRegisterService;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class MeController {


    private final MeRegisterService meRegisterService;
    private final MeLoginService meLoginService;
    private final MeNickNameService meNickNameService;
    private final MeEmailService meEmailService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SuccessMessage>> register(@RequestBody RegisterRequest registerRequest) {
        meRegisterService.registerUser(registerRequest);
        return ResponseEntity.ok(ApiResponse.create(1000,SuccessMessage.createSuccessMessage("회원가입이 성공적으로 완료되었습니다.")));

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = meLoginService.loginUser(loginRequest);
        return ResponseEntity.ok(ApiResponse.create(1006,loginResponse));

    }

    @GetMapping("/nickname/check")
    public ResponseEntity<ApiResponse<SuccessMessage>> nicknameCheck(@RequestParam String nickname) {
        meNickNameService.nicknameCheckUser(nickname);
        return ResponseEntity.ok(ApiResponse.create(1010,SuccessMessage.createSuccessMessage("닉네임이 중복되지 않았습니다.")));
    }

    @GetMapping("/email/check")
    public ResponseEntity<ApiResponse<SuccessMessage>> emailCheck(@RequestParam String email) {
        meEmailService.emailCheckUser(email);
        return ResponseEntity.ok(ApiResponse.create(1012,SuccessMessage.createSuccessMessage("이메일이 중복되지 않았습니다.")));
    }





}

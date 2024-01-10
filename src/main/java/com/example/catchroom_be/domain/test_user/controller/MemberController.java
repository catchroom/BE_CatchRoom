package com.example.catchroom_be.domain.test_user.controller;

import com.example.catchroom_be.domain.test_user.dto.MemberLogInRequest;
import com.example.catchroom_be.domain.test_user.dto.MemberSignUpRequest;
import com.example.catchroom_be.domain.test_user.service.MemberService;
import com.example.catchroom_be.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberSignUpRequest memberSignUpRequest) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse.create(1000, memberService.signup(memberSignUpRequest))
            );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLogInRequest memberLogInRequest) {
        return ResponseEntity
            .ok()
            .body(
                ApiResponse.create(1006, memberService.login(memberLogInRequest))
            );
    }

}

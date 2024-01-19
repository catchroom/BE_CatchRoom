package com.example.catchroom_be.domain.user.controller.mypage;

import com.example.catchroom_be.domain.user.dto.request.AccountNumRequest;
import com.example.catchroom_be.domain.user.dto.response.DepositAccountNumResponse;
import com.example.catchroom_be.domain.user.dto.response.ProfileResponse;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.service.mypage.MyPageAccountService;
import com.example.catchroom_be.domain.user.service.mypage.MyPageLogOutService;
import com.example.catchroom_be.domain.user.service.mypage.MyPageProfileService;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.global.exception.SuccessMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/mypage")
public class MyPageController {

    private final MyPageLogOutService myPageLogOutService;
    private final MyPageProfileService myPageProfileService;
    private final MyPageAccountService accountNumSetService;

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<SuccessMessage>> logout(HttpServletRequest request, @AuthenticationPrincipal User user) {
        myPageLogOutService.logoutService(request,user);
        return ResponseEntity.ok(ApiResponse.create(2000, SuccessMessage.createSuccessMessage("로그아웃이 성공적으로 완료되었습니다.")));
    }

    @PutMapping("/profile/nickname")
    public ResponseEntity<ApiResponse<SuccessMessage>> nickNameRefact(@RequestParam String nickName, @AuthenticationPrincipal User user) {
        if (nickName == null || !nickName.matches("^[a-zA-Z0-9가-힣]*$")) {
            throw new UserException(ErrorCode.USER_NICKNAME_NOT_VALID);
        }
        myPageProfileService.nicknameRefactService(nickName,user);
        return ResponseEntity.ok(ApiResponse.create(2002,SuccessMessage.createSuccessMessage("닉네임 수정이 성공적으로 완료되었습니다.")));
    }
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> profileFind(@AuthenticationPrincipal User user) {
        ProfileResponse profileResponse = myPageProfileService.profileFindService(user);
        return ResponseEntity.ok(ApiResponse.create(2004,profileResponse));
    }


    @GetMapping("/deposit/accountnum")
    public ResponseEntity<ApiResponse<DepositAccountNumResponse>> depositAccountNumFind(@AuthenticationPrincipal User user) {
        DepositAccountNumResponse depositAccountNumResponse = accountNumSetService.depositAccountNumFindService(user);
        return ResponseEntity.ok(ApiResponse.create(2005,depositAccountNumResponse));
    }

    @PostMapping("/accountnum")
    public ResponseEntity<ApiResponse<SuccessMessage>> accountNumSet(@AuthenticationPrincipal User user,@RequestBody AccountNumRequest accountNumRequest) {
        accountNumSetService.accountNumSetService(user,accountNumRequest);
        return ResponseEntity.ok(ApiResponse.create(2006,SuccessMessage.createSuccessMessage("예치금 계좌 등록이 완료되었습니다.")));
    }
    @PutMapping("/accountnum")
    public ResponseEntity<ApiResponse<SuccessMessage>> accountNumPut(@AuthenticationPrincipal User user,@RequestBody AccountNumRequest accountNumRequest) {
        accountNumSetService.accountNumPutService(user,accountNumRequest);
        return ResponseEntity.ok(ApiResponse.create(2010,SuccessMessage.createSuccessMessage("예치금 계좌 수정이 완료되었습니다.")));
    }
    @DeleteMapping("/accountnum")
    public ResponseEntity<ApiResponse<SuccessMessage>> accountNumDelete(@AuthenticationPrincipal User user) {
        accountNumSetService.accountNumDeleteService(user);
        return ResponseEntity.ok(ApiResponse.create(2011,SuccessMessage.createSuccessMessage("예치금 계좌 삭제가 완료되었습니다.")));
    }



}

package com.example.catchroom_be.domain.user.controller.mypage;

import com.example.catchroom_be.domain.user.exception.UserException;
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

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<SuccessMessage>> logout(HttpServletRequest request, @AuthenticationPrincipal User user) {
        myPageLogOutService.logoutService(request,user);
        return ResponseEntity.ok(ApiResponse.create(2000, SuccessMessage.createSuccessMessage("로그아웃이 성공적으로 완료되었습니다.")));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<SuccessMessage>> profileRefact(@RequestParam String nickName, @AuthenticationPrincipal User user) {
        if (nickName == null || !nickName.matches("^[a-zA-Z0-9가-힣]*$")) {
            throw new UserException(ErrorCode.USER_NICKNAME_NOT_VALID);
        }
        myPageProfileService.profileRefactService(nickName,user);
        return ResponseEntity.ok(ApiResponse.create(2002,SuccessMessage.createSuccessMessage("프로필 수정이 성공적으로 완료되었습니다.")));
    }
    @GetMapping("/nickname")
    public ResponseEntity<ApiResponse<String>> nicknameFind(@AuthenticationPrincipal User user) {
        String nickName = myPageProfileService.nickNameFindService(user);
        return ResponseEntity.ok(ApiResponse.create(2004,nickName));
    }

    

}

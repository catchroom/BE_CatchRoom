package com.example.catchroom_be.domain.user.controller.mypage;

import com.example.catchroom_be.domain.review.enumlist.ReviewType;
import com.example.catchroom_be.domain.user.dto.request.AccountNumRequest;
import com.example.catchroom_be.domain.user.dto.request.ReviewPostRequest;
import com.example.catchroom_be.domain.user.dto.request.ReviewRefactRequest;
import com.example.catchroom_be.domain.user.dto.response.*;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.service.mypage.*;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.global.exception.SuccessMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/mypage")
public class MyPageController {

    private final MyPageLogOutService myPageLogOutService;
    private final MyPageProfileService myPageProfileService;
    private final MyPageAccountService myPageAccountService;
    private final MyPageSalesHistoryService myPageSalesHistoryService;
    private final MyPagePurchaseHistoryService myPagePurchaseHistoryService;
    private final MyPageReviewService myPageReviewService;

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
        DepositAccountNumResponse depositAccountNumResponse = myPageAccountService.depositAccountNumFindService(user);
        return ResponseEntity.ok(ApiResponse.create(2005,depositAccountNumResponse));
    }

    @PostMapping("/accountnum")
    public ResponseEntity<ApiResponse<SuccessMessage>> accountNumSet(@AuthenticationPrincipal User user,@RequestBody AccountNumRequest accountNumRequest) {
        myPageAccountService.accountNumSetService(user,accountNumRequest);
        return ResponseEntity.ok(ApiResponse.create(2006,SuccessMessage.createSuccessMessage("예치금 계좌 등록이 완료되었습니다.")));
    }
    @PutMapping("/accountnum")
    public ResponseEntity<ApiResponse<SuccessMessage>> accountNumPut(@AuthenticationPrincipal User user,@RequestBody AccountNumRequest accountNumRequest) {
        myPageAccountService.accountNumPutService(user,accountNumRequest);
        return ResponseEntity.ok(ApiResponse.create(2010,SuccessMessage.createSuccessMessage("예치금 계좌 수정이 완료되었습니다.")));
    }
    @DeleteMapping("/accountnum")
    public ResponseEntity<ApiResponse<SuccessMessage>> accountNumDelete(@AuthenticationPrincipal User user) {
        myPageAccountService.accountNumDeleteService(user);
        return ResponseEntity.ok(ApiResponse.create(2011,SuccessMessage.createSuccessMessage("예치금 계좌 삭제가 완료되었습니다.")));
    }
    @PostMapping("deposit/withdraw")
    public ResponseEntity<ApiResponse<SuccessMessage>> depositWithdraw(@AuthenticationPrincipal User user,@RequestParam Long deposit) {
        myPageAccountService.depositWithdrawService(user,deposit);
        return ResponseEntity.ok(ApiResponse.create(2012,SuccessMessage.createSuccessMessage("예치금 금액 출금이 완료되었습니다.")));
    }

    @GetMapping("deposit/detail")
    public ResponseEntity<ApiResponse<List<DepositResponse>>> depositList(@AuthenticationPrincipal User user) {
        List<DepositResponse> depositResponseList = myPageAccountService.depositListService(user);
        return ResponseEntity.ok(ApiResponse.create(2014,depositResponseList));
    }

    @GetMapping("saleshistory/now")
    public ResponseEntity<ApiResponse<List<SalesHistoryNowResponse>>> salesHistoryNow(@AuthenticationPrincipal User user) {
        List<SalesHistoryNowResponse> salesHistoryNowResponseList = myPageSalesHistoryService.salesHistoryNowService(user);
        return ResponseEntity.ok(ApiResponse.create(2015, salesHistoryNowResponseList));
    }

    @GetMapping("saleshistory/done")
    public ResponseEntity<ApiResponse<List<SalesHistoryDoneResponse>>> salesHistoryDone(@AuthenticationPrincipal User user) {
        List<SalesHistoryDoneResponse> salesHistoryDoneResponseList = myPageSalesHistoryService.salesHistoryDoneService(user);
        return ResponseEntity.ok(ApiResponse.create(2015, salesHistoryDoneResponseList));
    }
    @GetMapping("/purchasehistory")
    public ResponseEntity<ApiResponse<List<PurChaseHistoryResponse>>> purchaseHistory(@AuthenticationPrincipal User user) {
        List<PurChaseHistoryResponse> purChaseHistoryResponseList = myPagePurchaseHistoryService.purchaseHistoryService(user);
        return ResponseEntity.ok(ApiResponse.create(2018,purChaseHistoryResponseList));
    }

    @GetMapping("/review")
    public ResponseEntity<ApiResponse<ReviewResponse>> reviewFind(@RequestParam String type,@RequestParam Long reviewId) {
        ReviewType reviewType = ReviewType.fromString(type);
        ReviewResponse reviewResponse = myPageReviewService.reviewFindService(reviewType,reviewId);
        return ResponseEntity.ok(ApiResponse.create(2016,reviewResponse));
    }

    @PostMapping("/review")
    public ResponseEntity<ApiResponse<SuccessMessage>> reviewPost(@AuthenticationPrincipal User user,@RequestBody ReviewPostRequest reviewPostRequest) {
        myPageReviewService.reviewPostService(user,reviewPostRequest);
        return ResponseEntity.ok(ApiResponse.create(2021,SuccessMessage.createSuccessMessage("리뷰 작성에 성공하셨습니다.")));
    }
    @PutMapping("/review")
    public ResponseEntity<ApiResponse<SuccessMessage>> reviewRefact(@RequestBody ReviewRefactRequest reviewRefactRequest) {
        myPageReviewService.reviewRefactService(reviewRefactRequest);
        return ResponseEntity.ok(ApiResponse.create(2023,SuccessMessage.createSuccessMessage("리뷰 수정에 성공하셨습니다.")));
    }
    @DeleteMapping("/review")
    public ResponseEntity<ApiResponse<SuccessMessage>> reviewDelete(@RequestParam String type,@RequestParam Long reviewId) {
        ReviewType reviewType = ReviewType.fromString(type);
        myPageReviewService.reviewDeleteService(reviewType,reviewId);
        return ResponseEntity.ok(ApiResponse.create(2025,SuccessMessage.createSuccessMessage("리뷰 삭제에 성공하셨습니다.")));
    }







}

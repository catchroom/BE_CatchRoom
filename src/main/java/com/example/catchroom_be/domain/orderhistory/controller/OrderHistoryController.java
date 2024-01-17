package com.example.catchroom_be.domain.orderhistory.controller;

import com.example.catchroom_be.domain.orderhistory.service.OrderHistoryService;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/orderhistory")
@RequiredArgsConstructor
public class OrderHistoryController {
    private final OrderHistoryService orderHistoryService;
    //TODO @AuthenticationPrincipal 로직 완성 예정_정혜민
    @GetMapping("/yanolja/product/candidate")
    public ResponseEntity<?> findProductCandidate(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                ApiResponse.create(
                    4000, orderHistoryService.findProductCandidate(user)
                )
            );
    }
}

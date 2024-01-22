package com.example.catchroom_be.domain.orderhistory.controller;

import com.example.catchroom_be.domain.orderhistory.service.OrderHistoryService;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/orderhistory")
@RequiredArgsConstructor
public class OrderHistoryController {
    private final OrderHistoryService orderHistoryService;

    @GetMapping("/yanolja/product/candidate")
    public ResponseEntity<?> findProductCandidate(@AuthenticationPrincipal User user) {
        if (orderHistoryService.findProductCandidate(user).isEmpty()) {
            return ResponseEntity.ok(
                ApiResponse.create(
                    4001, "야놀자에서 구매하신 숙박권이 없습니다."
                )
            );
        } else {
            return ResponseEntity.ok(
                ApiResponse.create(
                    4000, orderHistoryService.findProductCandidate(user)
                )
            );
        }

    }
}

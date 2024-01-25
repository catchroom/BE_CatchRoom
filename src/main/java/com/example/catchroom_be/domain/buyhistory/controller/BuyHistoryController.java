package com.example.catchroom_be.domain.buyhistory.controller;

import com.example.catchroom_be.domain.buyhistory.dto.request.BuyRequest;
import com.example.catchroom_be.domain.buyhistory.service.BuyHistoryService;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/buy")
@RequiredArgsConstructor
public class BuyHistoryController {

    private final BuyHistoryService buyHistoryService;
    @PostMapping("")
    public ApiResponse<?> buyProduct(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid BuyRequest buyRequest
    ) {

        return buyHistoryService.buyProduct(user, buyRequest);
    }

    @GetMapping("/productInfo")
    public ApiResponse<?> productInfo(
            @AuthenticationPrincipal User user,
            @RequestParam Long productId) {

        System.out.println("productId = " + productId);

        return buyHistoryService.getPaymentInfo(user, productId);
    }

    @GetMapping("/mypage/purchasehistory/detail")
    public ApiResponse<?> getPurchaseHistory(
            @AuthenticationPrincipal User user,
            @RequestParam Long productId
    ) {

        return buyHistoryService.purchaseHistory(user, productId);
    }
}
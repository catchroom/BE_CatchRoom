package com.example.catchroom_be.domain.wish.controller;

import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.wish.service.WishService;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/buy")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    @PostMapping("/wish")
    public ApiResponse<?> wish(
            @RequestParam Long productId,
            @AuthenticationPrincipal User user
    ) {
        return wishService.setWishList(productId, user);
    }
}

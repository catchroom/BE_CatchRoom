package com.example.catchroom_be.domain.product.controller;


import com.example.catchroom_be.domain.product.service.ProductService;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<?> findProduct(@RequestParam(name = "id") Long productId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
            ApiResponse.create(
                4040, productService.findProduct(productId,user)));
    }

}

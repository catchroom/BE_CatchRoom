package com.example.catchroom_be.domain.product.controller;

import com.example.catchroom_be.domain.product.dto.request.SaleEditRequest;
import com.example.catchroom_be.domain.product.dto.request.SaleRegistRequest;
import com.example.catchroom_be.domain.product.service.SaleService;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.exception.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleservice;

    @GetMapping("/yanolja/product/detail")
    public ResponseEntity<?> findYanoljaReservationInfo(
        @RequestParam("id") Long orderHistoryId) {
        return ResponseEntity.ok(
            ApiResponse.create(
                4002, saleservice.findYanoljaReservationInfo(orderHistoryId)));
    }

    @PostMapping("/product")
    public ResponseEntity<?> registerProduct(
        @RequestBody SaleRegistRequest productRegisterRequest,
        @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
            ApiResponse.create(
                4010, saleservice.registerProduct(productRegisterRequest,user)));
    }

    @PutMapping("/product")
    public ResponseEntity<?> editProduct(
        @RequestParam("id") Long productId,
        @RequestBody SaleEditRequest saleEditRequest,
        @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
            ApiResponse.create(
                4020, saleservice.editProduct(productId,saleEditRequest,user)));
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(
        @RequestParam("id") Long productId,
        @AuthenticationPrincipal User user) {
        saleservice.deleteProduct(productId,user);
        return ResponseEntity.ok(
            ApiResponse.create(
                4030, SuccessMessage.createSuccessMessage("상품 삭제에 성공하셨습니다.")));
    }

    @GetMapping("/edit/info/product")
    public ResponseEntity<?> findProductAllInfo(
        @RequestParam("id") Long productId,
        @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
            ApiResponse.create(
                4050, saleservice.findProductAllInfo(productId,user)));
    }
}

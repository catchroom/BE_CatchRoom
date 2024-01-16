package com.example.catchroom_be.domain.product.controller;

import com.example.catchroom_be.domain.product.dto.request.SaleEditRequest;
import com.example.catchroom_be.domain.product.dto.request.SaleRegistRequest;
import com.example.catchroom_be.domain.product.service.SaleService;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleservice;

    //TODO @AuthenticationPrincipal 추가 예정_정혜민
    @GetMapping("/yanolja/product/detail")
    public ResponseEntity<?> findProductDetailInfo(@RequestParam("id") Long orderHistoryId) {
        return ResponseEntity.ok(
            ApiResponse.create(
                4002, saleservice.findProductDetailInfo(orderHistoryId)));
    }

    //TODO @AuthenticationPrincipal 추가 예정_정혜민

    @PostMapping("/product")
    public ResponseEntity<?> registerProduct(@RequestBody SaleRegistRequest productRegisterRequest) {
        return ResponseEntity.ok(
            ApiResponse.create(
                4010, saleservice.registerProduct(productRegisterRequest)));
    }

    //TODO @AuthenticationPrincipal 추가 예정_정혜민
    @PutMapping("/product")
    public ResponseEntity<?> editProduct(@RequestParam("id") Long productId, @RequestBody SaleEditRequest saleEditRequest) {
        return ResponseEntity.ok(
            ApiResponse.create(
                4020, saleservice.editProduct(productId,saleEditRequest)));
    }
}

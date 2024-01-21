package com.example.catchroom_be.domain.product.controller;

import com.example.catchroom_be.domain.product.service.ProductSearchService;
import com.example.catchroom_be.domain.product.type.ProductSortType;
import com.example.catchroom_be.global.common.ApiResponse;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductSearchController {
    private final ProductSearchService productSearchService;

    @GetMapping("/search")
    public ResponseEntity<?> getSearchList (
          @RequestParam(name = "accommodationType") String accommodationType,
          @RequestParam(name = "region") String region,
          @RequestParam(name = "pax") int pax,
          @RequestParam(name = "checkInStart") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInStart,
          @RequestParam(name = "checkInEnd") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInEnd,
          @RequestParam(name = "filter") ProductSortType filter,
          Pageable pageable // TODO 무한스크롤 구현 예정
    ) {

        return ResponseEntity.ok(
                ApiResponse.create(3005, productSearchService.getSearchList(
                        accommodationType, region, pax, checkInStart, checkInEnd, filter, pageable
                ))
        );
    }
}

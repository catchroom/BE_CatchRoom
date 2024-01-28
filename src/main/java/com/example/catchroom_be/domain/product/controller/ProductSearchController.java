package com.example.catchroom_be.domain.product.controller;

import com.example.catchroom_be.domain.product.service.ProductSearchService;
import com.example.catchroom_be.domain.product.type.ProductSortType;
import com.example.catchroom_be.global.common.ApiResponse;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
            @RequestParam(name = "type") String type,
            @RequestParam(name = "region") String region,
            @RequestParam(name = "pax") int pax,
            @RequestParam(name = "checkInStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInStart,
            @RequestParam(name = "checkInEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkInEnd,
            @RequestParam(name = "filter") ProductSortType filter,
            @RequestParam(name = "pageNumber") Integer pageNumber
    ) {

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, 10);

        return ResponseEntity.ok(
                ApiResponse.create(3005, productSearchService.getSearchList(
                        type, region, pax, checkInStart, checkInEnd, filter, pageRequest
                ))
        );
    }
}

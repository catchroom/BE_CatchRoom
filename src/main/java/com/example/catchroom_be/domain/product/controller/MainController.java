package com.example.catchroom_be.domain.product.controller;

import com.example.catchroom_be.domain.product.service.MainService;
import com.example.catchroom_be.domain.product.type.ProductSortType;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/main")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/product/list")
    public ResponseEntity<?> getSearchList(
            @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "filter", required = false) ProductSortType filter,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "pageNumber", required = false) int pageNumber, // page는 0부터 시작
            @RequestParam(name = "dataType") int dataType
    ) {

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, 10);

        //캐치특가 - 메인홈
        if (dataType == 1) {
            return ResponseEntity.ok(
                    ApiResponse.create(3004, mainService.catchMain(pageRequest))
            );
        }

        //캐치특가 - 전체 보기
        else if (dataType == 2) {
            return ResponseEntity.ok(
                    ApiResponse.create(3004, mainService.catchAll(filter, region, pageRequest))
            );
        }

        // 체크인 임박  - 메인홈
        else if (dataType == 3) {

            pageRequest = PageRequest.of(pageNumber - 1, 3);

            return ResponseEntity.ok(
                    ApiResponse.create(3004, mainService.checkInMain(date, pageRequest))
            );
        }

        // 체크인 임박 - 전체보기
        else {
            return ResponseEntity.ok(
                    ApiResponse.create(3004, mainService.checkInAll(date, filter, region, pageRequest))
            );
        }
    }

    @GetMapping("/review/list")
    public ResponseEntity<?> getSearchList(){

        return null;
    }
}

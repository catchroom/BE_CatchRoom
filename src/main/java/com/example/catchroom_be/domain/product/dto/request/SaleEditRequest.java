package com.example.catchroom_be.domain.product.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class SaleEditRequest {
    //    private User seller; TODO 회원 도메인 완성 시 추가
    private int discountRate;
    private int sellPrice;
    private int actualProfit;
    private int catchPrice;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    private String introduction;
    private Boolean isAutoCatch;
    private Boolean isCatch;
    private Boolean isNego;
    private LocalDate catchPriceStartDate;
    private String accommodationName;
}

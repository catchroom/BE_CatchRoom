package com.example.catchroom_be.domain.product.dto.request;

import com.example.catchroom_be.domain.product.type.DealState;
import lombok.*;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

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
    private LocalDateTime endDate;
    private String introduction;
    private boolean isAutoCatch;
    private boolean isCatch;
    private boolean isNego;
    private LocalDate catchPriceStartDate;
    private String accommodationName;
}

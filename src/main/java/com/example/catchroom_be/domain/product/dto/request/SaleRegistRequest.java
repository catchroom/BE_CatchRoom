package com.example.catchroom_be.domain.product.dto.request;

import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.type.DealState;
import com.example.catchroom_be.domain.user.entity.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class SaleRegistRequest {
    private User seller;
    private Long orderHistoryId;
    private DealState dealState;
    private int discountRate;
    private int sellPrice;
    private int actualProfit;
    private int catchPrice;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    private String introduction;
    private boolean isAutoCatch;
    private boolean isCatch;
    private boolean isNego;
    private LocalDate catchPriceStartDate;
    private String accommodationName;

    public Product toEntity(OrderHistory orderHistory,User loginUser) {
        return Product.builder()
            .seller(loginUser)
            .orderHistory(orderHistory)
            .dealState(dealState.ONSALE)
            .discountRate(discountRate)
            .sellPrice(sellPrice)
            .actualProfit(actualProfit)
            .catchPrice(catchPrice)
            .endDate(endDate)
            .introduction(introduction)
            .isAutoCatch(isAutoCatch)
            .isCatch(isCatch)
            .isNego(isNego)
            .catchPriceStartDate(catchPriceStartDate)
            .accommodationName(accommodationName)
            .build();
    }

}

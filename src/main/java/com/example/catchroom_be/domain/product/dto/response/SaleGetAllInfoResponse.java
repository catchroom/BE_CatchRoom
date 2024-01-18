package com.example.catchroom_be.domain.product.dto.response;

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
public class SaleGetAllInfoResponse {
    private String accommdationName;
    private String roomType;
    private int price;
    private String accommdationUrl;
    private LocalDate checkIn;
    private LocalDate checkOut;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    private int discountRate;
    private int sellPrice;
    private Boolean isCatch;
    private int actualProfit;
    private Boolean isAutoCatch;
    private int catchPrice;
    private LocalDate catchPriceStartDate;
    private String introduction;
    private Boolean isNego;

    public static SaleGetAllInfoResponse fromEntity(Product product) {
        return SaleGetAllInfoResponse.builder()
            .accommdationName(product.getAccommodationName())
            .roomType(product.getOrderHistory().getRoom().getName())
            .price(product.getSellPrice())
            .accommdationUrl(product.getOrderHistory().getAccommodation().getThumbnailUrl())
            .checkIn(product.getOrderHistory().getCheckIn())
            .checkOut(product.getOrderHistory().getCheckOut())
            .endDate(product.getEndDate())
            .discountRate(product.getDiscountRate())
            .sellPrice(product.getSellPrice())
            .isCatch(product.getIsCatch())
            .actualProfit(product.getActualProfit())
            .isAutoCatch(product.getIsAutoCatch())
            .catchPrice(product.getCatchPrice())
            .catchPriceStartDate(product.getCatchPriceStartDate())
            .introduction(product.getIntroduction())
            .isNego(product.getIsNego())
            .build();
    }
}

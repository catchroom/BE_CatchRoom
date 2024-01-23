package com.example.catchroom_be.domain.user.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class SalesHistoryDoneResponse {
    String accomodationName;
    LocalDate checkIn;
    LocalDate checkOut;
    LocalDateTime wirteDate;
    LocalDateTime endDate;
    Integer sellPrice;
    String thumbNailUrl;
    Boolean isCatch;
    Long orderHistoryId;
    String dealState;
    Long reviewId;
    String ReviewStatusType;

    public void fromProduct(LocalDate checkIn, LocalDate checkOut, LocalDateTime wirteDate,
                            LocalDateTime endDate, Integer sellPrice, Boolean isCatch,
                            Long orderHistoryId,String dealState,Long reviewId,String ReviewStatusType) {
        this.accomodationName = accomodationName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.wirteDate = wirteDate;
        this.endDate = endDate;
        this.sellPrice = sellPrice;
        this.thumbNailUrl = thumbNailUrl;
        this.isCatch = isCatch;
        this.orderHistoryId = orderHistoryId;
        this.dealState = dealState;
        this.reviewId = reviewId;
        this.ReviewStatusType = ReviewStatusType;
    }

    public void fromAccommodation(String accommodationName,String thumbNailUrl) {
        this.accomodationName = accommodationName;
        this.thumbNailUrl = thumbNailUrl;
    }
}

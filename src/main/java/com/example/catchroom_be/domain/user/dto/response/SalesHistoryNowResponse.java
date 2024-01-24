package com.example.catchroom_be.domain.user.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter

public class SalesHistoryNowResponse {

    String accommodationName;
    LocalDate checkIn;
    LocalDate checkOut;
    LocalDateTime wirteDate;
    LocalDateTime endDate;
    Integer sellPrice;
    String thumbNailUrl;
    Boolean isCatch;
    Long orderHistoryId;
    Long productId;

    public void fromProduct(LocalDate checkIn, LocalDate checkOut, LocalDateTime wirteDate,
                            LocalDateTime endDate, Integer sellPrice, Boolean isCatch, Long orderHistoryId,Long productId) {

        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.wirteDate = wirteDate;
        this.endDate = endDate;
        this.sellPrice = sellPrice;
        this.isCatch = isCatch;
        this.orderHistoryId = orderHistoryId;
        this.productId = productId;
    }

    public void fromAccommodation(String accommodationName,String thumbNailUrl) {
        this.accommodationName = accommodationName;
        this.thumbNailUrl = thumbNailUrl;
    }
}

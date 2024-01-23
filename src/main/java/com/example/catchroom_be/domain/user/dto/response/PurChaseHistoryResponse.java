package com.example.catchroom_be.domain.user.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PurChaseHistoryResponse {
    String accommodationName;
    LocalDate checkIn;
    LocalDate checkOut;
    LocalDateTime buyDate;
    Integer buyPrice;
    Boolean isCatch;
    String thumbNailUrl;
    Long reviewId;

    public void fromEntity(LocalDateTime buyDate,Boolean isCatch,Integer buyPrice,
                                              LocalDate checkIn,LocalDate checkOut,
                                              String accommodationName,String thumbNailUrl,Long reviewId) {
        this. buyDate = buyDate;
        this.isCatch = isCatch;
        this.buyPrice = buyPrice;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.accommodationName = accommodationName;
        this.thumbNailUrl = thumbNailUrl;
        this.reviewId = reviewId;
    }

}

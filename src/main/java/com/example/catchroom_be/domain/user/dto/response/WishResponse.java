package com.example.catchroom_be.domain.user.dto.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WishResponse {
    String accommodationName;
    LocalDate checkIn;
    LocalDate checkOut;
    Boolean isCatch;
    String roomName;
    Integer sellPrice;
    double discountRate;
    double discoutPrice;
    Long wishId;


    public WishResponse(String accommodationName,LocalDate checkIn
    ,LocalDate checkOut,Boolean isCatch,String roomName,
                        Integer sellPrice,double discountRate,
                        double discoutPrice,Long wishId) {

        this.accommodationName = accommodationName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.isCatch = isCatch;
        this.roomName = roomName;
        this.sellPrice = sellPrice;
        this.discountRate = discountRate;
        this.discoutPrice = discoutPrice;
        this.wishId = wishId;

    }
}

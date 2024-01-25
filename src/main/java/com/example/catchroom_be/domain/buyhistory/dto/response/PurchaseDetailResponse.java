package com.example.catchroom_be.domain.buyhistory.dto.response;

import com.example.catchroom_be.domain.product.type.TransportationType;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

public class PurchaseDetailResponse extends ApiResponse<PurchaseDetailResponse.PurchaseHistoryDetailData> {

    public PurchaseDetailResponse(int code, PurchaseHistoryDetailData data) {
        super(code, data);
    }

    public static PurchaseDetailResponse create(int code, PurchaseHistoryDetailData data) {
        return new PurchaseDetailResponse(code, data);
    }

    @Getter
    @RequiredArgsConstructor
    public static class PurchaseHistoryDetailData {
        private final BuyerInfo buyer;
        private final UserInfo user;
        private final AccommodationInfo accommodation;
        private final SellPriceInfo sellPrice;
        private final SellerInfo seller;
        private final String introduction;
        private final String paymentMethod;
    }


    @Getter
    @RequiredArgsConstructor
    public static class BuyerInfo {
        private final String buyerName;
        private final String buyerPhoneNumber;
    }

    @Getter
    @RequiredArgsConstructor
    public static class UserInfo {
        private final String userName;
        private final String userPhoneNumber;
    }

    @Getter
    @RequiredArgsConstructor
    public static class AccommodationInfo {
        private final String reservationNumber;
        private final String image;
        private final String accommodationName;
        private final String address;
        private final LocalDate checkIn;
        private final LocalDate checkOut;
        private final Long period;
        private final TransportationType transportation;
        private final String roomName;
        private final int normalCapacity;
        private final int maxCapacity;
    }

    @Getter
    @RequiredArgsConstructor
    public static class SellPriceInfo {
        private final int sellPrice;
        private final int discount;
        private final int price;
    }

    @Getter
    @RequiredArgsConstructor
    public static class SellerInfo {
        private final String nickName;
    }
}

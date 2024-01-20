package com.example.catchroom_be.domain.buyhistory.dto.response;

import com.example.catchroom_be.domain.accommodation.type.AccommodationType;
import com.example.catchroom_be.domain.product.type.TransportationType;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

public class PaymentResponse extends ApiResponse<PaymentResponse.PaymentResponseData> {

    public PaymentResponse(int code, PaymentResponse.PaymentResponseData data) {
        super(code, data);
    }

    public static PaymentResponse create(int code, PaymentResponse.PaymentResponseData data) {
        return new PaymentResponse(code, data);
    }

    @Getter
    @RequiredArgsConstructor
    public static class PaymentResponseData {
        private final PaymentResponse.ProductInfo product;
        private final PaymentResponse.BuyerInfo buyer;
        private final PaymentResponse.PaymentInfo payment;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ProductInfo {
        private final String productName;
        private final String accommodationName;
        private final LocalDate checkInDate;
        private final LocalDate checkOutDate;
    }


    @Getter
    @RequiredArgsConstructor
    public static class BuyerInfo {
        private final String buyerName;
        private final String buyerPhoneNumber;
    }

    @Getter
    @RequiredArgsConstructor
    public static class PaymentInfo {
        private final int sellPrice;
        private final int commissionPrice;
        private final int price;
    }
}

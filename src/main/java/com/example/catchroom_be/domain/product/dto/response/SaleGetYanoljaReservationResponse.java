package com.example.catchroom_be.domain.product.dto.response;

import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class SaleGetYanoljaReservationResponse {
    private String accommdationName;
    private String roomType;
    private int price;
    private String accommdationUrl;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public static SaleGetYanoljaReservationResponse fromEntity(OrderHistory orderHistory) {
        return SaleGetYanoljaReservationResponse.builder()
            .accommdationName(orderHistory.getAccommodation().getName())
            .roomType(orderHistory.getRoom().getName())
            .price(orderHistory.getPrice())
            .accommdationUrl(orderHistory.getAccommodation().getThumbnailUrl())
            .checkIn(orderHistory.getCheckIn())
            .checkOut(orderHistory.getCheckOut())
            .build();
    }
}

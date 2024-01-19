package com.example.catchroom_be.domain.orderhistory.dto;

import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class OrderHistoryCandidateResponse {
    private Long id;
    private String accommdationName;
    private int price;
    private LocalDate checkIn;
    private LocalDate checkOut;

    public static OrderHistoryCandidateResponse fromEntity(OrderHistory orderHistory) {
        return OrderHistoryCandidateResponse.builder()
            .id(orderHistory.getId())
            .accommdationName(orderHistory.getAccommodation().getName())
            .price(orderHistory.getPrice())
            .checkIn(orderHistory.getCheckIn())
            .checkOut(orderHistory.getCheckOut())
            .build();
    }
}

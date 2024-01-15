package com.example.catchroom_be.domain.orderhistory.entity;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.accommodation.entity.Room;
import com.example.catchroom_be.domain.product.type.TransportationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name =  "order_history")
@Getter
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private boolean isSale;

    private boolean isFreeCancel;

    @Enumerated(EnumType.STRING)
    private TransportationType transportation;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Member user;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


}

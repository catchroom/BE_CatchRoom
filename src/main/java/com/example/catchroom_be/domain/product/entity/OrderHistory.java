package com.example.catchroom_be.domain.product.entity;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.accommodation.entity.Room;
import com.example.catchroom_be.domain.product.type.TransportationType;
import com.example.catchroom_be.domain.test_user.entity.UserTest;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTest user;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


}

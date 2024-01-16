package com.example.catchroom_be.domain.orderhistory.entity;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.accommodation.entity.Room;
import com.example.catchroom_be.domain.product.type.TransportationType;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name =  "order_history")
@Getter
public class OrderHistory extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "price",nullable = false)
    private int price;
    @Column(name = "check_in")
    private LocalDate checkIn;
    @Column(name = "check_out")
    private LocalDate checkOut;
    @Column(name = "is_sale", nullable = false)
    private boolean isSale;
    @Column(name = "is_free_cancel", nullable = false)
    private boolean isFreeCancel;
    @Column(name = "transportation")
    @Enumerated(EnumType.STRING)
    private TransportationType transportation;

    public void updateSaleState(boolean state) {
        this.isSale = state;
    }

}

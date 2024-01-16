package com.example.catchroom_be.domain.product.entity;

import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.product.type.DealState;
import com.example.catchroom_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.context.annotation.Lazy;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderhistory_id")
    private OrderHistory orderHistory;

    @Enumerated(EnumType.STRING)
    private DealState dealState;
    private int discountRate;
    private int sellPrice;
    private int actualProfit;
    private int catchPrice;
    private LocalDateTime endDate;
    private String introduction;
    private boolean isAutoCatch;
    private boolean isCatch;
    private boolean isNego;
    private LocalDate catchPriceStartDate;
    private String accommodationName;

}

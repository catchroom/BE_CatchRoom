package com.example.catchroom_be.domain.product.entity;

import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.product.dto.request.SaleEditRequest;
import com.example.catchroom_be.domain.product.type.DealState;
import com.example.catchroom_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

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
    @JoinColumn(name = "user_id")
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
    @Column(name = "end_date")
    private LocalDateTime endDate;
    private String introduction;
    private boolean isAutoCatch;
    private boolean isCatch;
    private boolean isNego;
    private LocalDate catchPriceStartDate;
    private String accommodationName;

    public void updateProduct(SaleEditRequest saleEditRequest) {
        this.discountRate = saleEditRequest.getDiscountRate();
        this.sellPrice = saleEditRequest.getSellPrice();
        this.actualProfit = saleEditRequest.getActualProfit();
        this.catchPrice = saleEditRequest.getCatchPrice();
        this.endDate = saleEditRequest.getEndDate();
        this.introduction = saleEditRequest.getIntroduction();
        this.isAutoCatch = saleEditRequest.isAutoCatch();
        this.isCatch = saleEditRequest.isCatch();
        this.isNego = saleEditRequest.isNego();
        this.catchPriceStartDate = saleEditRequest.getCatchPriceStartDate();
        this.accommodationName = saleEditRequest.getAccommodationName();

    }
}

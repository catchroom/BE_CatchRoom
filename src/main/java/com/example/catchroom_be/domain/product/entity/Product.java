package com.example.catchroom_be.domain.product.entity;

import com.example.catchroom_be.domain.orderhistory.entity.OrderHistory;
import com.example.catchroom_be.domain.product.dto.request.SaleEditRequest;
import com.example.catchroom_be.domain.product.type.DealState;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseTime {

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
    @Column(name = "deal_state")
    private DealState dealState;
    @Column(name = "discount_rate")
    private int discountRate;
    @Column(name = "sell_price")
    private int sellPrice;
    @Column(name = "actual_profit")
    private int actualProfit;
    @Column(name = "catch_price")
    private int catchPrice;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Size(min = 9, message = "최소 10글자 이상 입력해주세요.")
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "is_auto_catch")
    private Boolean isAutoCatch;
    @Column(name = "is_catch")
    private Boolean isCatch;
    @Column(name = "is_nego")
    private Boolean isNego;
    @Column(name = "catch_price_start_date")
    private LocalDate catchPriceStartDate;
    @Column(name = "accommodation_name")
    private String accommodationName;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public void updateProduct(SaleEditRequest saleEditRequest) {
        this.discountRate = saleEditRequest.getDiscountRate();
        this.sellPrice = saleEditRequest.getSellPrice();
        this.actualProfit = saleEditRequest.getActualProfit();
        this.catchPrice = saleEditRequest.getCatchPrice();
        this.endDate = saleEditRequest.getEndDate();
        this.introduction = saleEditRequest.getIntroduction();
        this.isAutoCatch = saleEditRequest.getIsAutoCatch();
        this.isCatch = saleEditRequest.getIsCatch();
        this.isNego = saleEditRequest.getIsNego();
        this.catchPriceStartDate = saleEditRequest.getCatchPriceStartDate();
        this.accommodationName = saleEditRequest.getAccommodationName();

    }

    public void updateDealState(DealState updateDealState) {
        this.dealState = updateDealState;
    }

    public void updateIsCatch(int catchPrice) {
        this.sellPrice = catchPrice;
        this.isCatch = true;
        this.actualProfit = catchPrice;
    }

    public void setIsDeleted(boolean delete) {
        this.isDeleted = delete;
    }

}

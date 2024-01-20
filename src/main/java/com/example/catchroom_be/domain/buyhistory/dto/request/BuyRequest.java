package com.example.catchroom_be.domain.buyhistory.dto.request;

import com.example.catchroom_be.domain.buyhistory.entity.BuyHistory;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.user.entity.User;
import org.jetbrains.annotations.NotNull;

public record BuyRequest(
        @NotNull Long productId,
        @NotNull UserInfo userInfo,
        @NotNull PaymentInfo paymentInfo,
        @NotNull String paymentMethod
) {

    public record UserInfo(
            @NotNull String userName,
            @NotNull String userPhoneNumber) {
    }

    public record PaymentInfo(
            @NotNull int sellPrice,
            @NotNull int price) {
    }

    public BuyHistory toEntity(User buyer, Product product) {
        return BuyHistory.builder()
                .buyer(buyer)
                .product(product)
                .price(this.paymentInfo.price())
                .isCatch(product.getIsCatch())
                .visitorName(this.userInfo.userName())
                .visitorPhoneNumber(this.userInfo.userPhoneNumber())
                .paymentMethod(this.paymentMethod())
                .build();
    }
}

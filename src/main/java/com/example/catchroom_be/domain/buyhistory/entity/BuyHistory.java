package com.example.catchroom_be.domain.buyhistory.entity;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.security.SecureRandom;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "buy_history")
@Getter
public class BuyHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int price;

    private boolean isCatch;

    private String visitorName;

    private String visitorPhoneNumber;

    private String paymentMethod;

    private String reservationNumber;

    @Builder
    private BuyHistory(User buyer, Product product, int price, Boolean isCatch, String visitorName, String visitorPhoneNumber, String paymentMethod) {
        this.buyer = buyer;
        this.product = product;
        this.price = price;
        this.isCatch = isCatch;
        this.visitorName = visitorName;
        this.visitorPhoneNumber = visitorPhoneNumber;
        this.paymentMethod = paymentMethod;
    }

    public void setReservationNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        this.reservationNumber = new SecureRandom()
                .ints(10, 0, characters.length())
                .mapToObj(characters::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}

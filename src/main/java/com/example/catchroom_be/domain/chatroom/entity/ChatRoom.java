package com.example.catchroom_be.domain.chatroom.entity;

import com.example.catchroom_be.domain.product.entity.Product;

import com.example.catchroom_be.domain.product.type.UserIdentity;
import com.example.catchroom_be.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String chatRoomNumber;

    @Transient
    private UserIdentity loginUserIdentity;

    public ChatRoom(User seller, User buyer, Product product, String chatRoomNumber) {
        this.seller = seller;
        this.buyer = buyer;
        this.product = product;
        this.chatRoomNumber = chatRoomNumber;
    }

    public void updateUserIdentity(Long userId) {
        if (this.buyer.getId().equals(userId)) {
            this.loginUserIdentity = UserIdentity.BUYER;
        } else {
            this.loginUserIdentity = UserIdentity.SELLER;
        }
    }

    public static ChatRoom create(User seller, User buyer, Product product) {
        String chatRoomNumber = UUID.randomUUID().toString();
        return new ChatRoom(seller,buyer,product,chatRoomNumber);
    }


}

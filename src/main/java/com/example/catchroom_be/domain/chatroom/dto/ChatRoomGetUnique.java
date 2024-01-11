package com.example.catchroom_be.domain.chatroom.dto;

import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.test_user.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ChatRoomGetUnique {

    private Long id;
    private Long sellerId;
    private Long buyerId;
    private Long productId;
    private String chatRoomNumber;

    @QueryProjection
    public ChatRoomGetUnique(Long sellerId, Long buyerId, Long productId) {
        this.id = id;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.productId = productId;
        this.chatRoomNumber = chatRoomNumber;
    }
}


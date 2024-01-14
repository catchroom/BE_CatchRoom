package com.example.catchroom_be.domain.chatroom.dto;

import com.querydsl.core.annotations.QueryProjection;

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


package com.example.catchroom_be.domain.chatroom.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ChatRoomInfoResponse {
    private String partnerNickName;

    private String productName;

    private String productImage;

    private int price;

    private Long productId;

    private Long buyerId;

    private Long sellerId;
}

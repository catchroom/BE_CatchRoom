package com.example.catchroom_be.domain.chatroom.dto.request;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomCreateRequest {

    private Long buyerId;
    private Long sellerId;
    private Long productId;

}

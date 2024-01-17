package com.example.catchroom_be.domain.chatroom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomCreateRequest {

    private Long buyerId;
    private Long sellerId;
    private Long productId;

}

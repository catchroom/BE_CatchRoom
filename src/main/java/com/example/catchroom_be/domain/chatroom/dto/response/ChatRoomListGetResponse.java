package com.example.catchroom_be.domain.chatroom.dto.response;

import com.example.catchroom_be.domain.chatroom.entity.ChatRoom;
import com.example.catchroom_be.domain.product.type.UserIdentity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomListGetResponse {
    private String chatRoomNumber;
    private Long buyerId;
    private Long sellerId;
    private Long productId;
    private UserIdentity loginUserIdentity;

    public static ChatRoomListGetResponse fromEntity(ChatRoom chatRoom) {
        return ChatRoomListGetResponse.builder()
            .chatRoomNumber(chatRoom.getChatRoomNumber())
            .buyerId(chatRoom.getBuyer().getId())
            .sellerId(chatRoom.getSeller().getId())
            .productId(chatRoom.getProduct().getId())
            .loginUserIdentity(chatRoom.getLoginUserIdentity())
            .build();
    }
}

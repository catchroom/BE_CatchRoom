package com.example.catchroom_be.domain.chatroom.dto.response;

import com.example.catchroom_be.domain.chatroom.entity.ChatRoom;
import com.example.catchroom_be.domain.chatroom.type.ChatRoomState;
import com.example.catchroom_be.domain.product.type.DealState;
import com.example.catchroom_be.domain.product.type.UserIdentity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomListGetResponse {
    private String chatRoomNumber;
    private Long buyerId;
    private Long sellerId;
    private Long productId;

    private String accommodationName;
    private int sellPrice;

    private UserIdentity loginUserIdentity;
    private String accommodationUrl;

    private String partnerNickName;

    private DealState dealState;
    private ChatRoomState buyerState;
    private ChatRoomState sellerState;

    private static String getPartnerNickName(ChatRoom chatRoom) {
        if (chatRoom.getLoginUserIdentity().equals(UserIdentity.SELLER)) {
            return chatRoom.getBuyer().getNickName();
        } else {
            return chatRoom.getSeller().getNickName();
        }
    }

    public static ChatRoomListGetResponse fromEntity(ChatRoom chatRoom) {

        return ChatRoomListGetResponse.builder()
                .chatRoomNumber(chatRoom.getChatRoomNumber())
                .buyerId(chatRoom.getBuyer().getId())
                .sellerId(chatRoom.getSeller().getId())
                .productId(chatRoom.getProduct().getId())
                .accommodationName(chatRoom.getProduct().getOrderHistory().getAccommodation().getName())
                .sellPrice(chatRoom.getProduct().getSellPrice())
                .loginUserIdentity(chatRoom.getLoginUserIdentity())
                .accommodationUrl(chatRoom.getProduct().getOrderHistory().getAccommodation().getThumbnailUrl())
                .partnerNickName(chatRoom.getBuyer().getNickName())
                .dealState(chatRoom.getProduct().getDealState())
                .buyerState(chatRoom.getBuyerState())
                .sellerState(chatRoom.getSellerState())
                .partnerNickName(
                        ChatRoomListGetResponse.getPartnerNickName(chatRoom)
                )
                .build();


    }
}

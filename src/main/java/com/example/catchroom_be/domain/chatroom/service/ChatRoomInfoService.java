package com.example.catchroom_be.domain.chatroom.service;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.chatroom.dto.response.ChatRoomInfoResponse;
import com.example.catchroom_be.domain.chatroom.entity.ChatRoom;
import com.example.catchroom_be.domain.chatroom.exception.ChatRoomException;
import com.example.catchroom_be.domain.chatroom.repository.ChatRoomRepository;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomInfoService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional(readOnly = true)
    public ChatRoomInfoResponse getChatRoomInfo(String roomId, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomNumber(roomId)
                .orElseThrow(() -> new ChatRoomException(ErrorCode.CHATROOM_NOT_FOUND));

        Product product = chatRoom.getProduct();
        Accommodation accommodation = product.getOrderHistory().getAccommodation();
        String partnerNickName = getPartnerNickName(user, chatRoom);

        return ChatRoomInfoResponse.builder()
                .partnerNickName(partnerNickName)
                .productId(product.getId())
                .productName(product.getAccommodationName())
                .productImage(accommodation.getThumbnailUrl())
                .price(product.getSellPrice())
                .buyerId(chatRoom.getBuyer().getId())
                .sellerId(chatRoom.getSeller().getId())
                .build();

    }

    private String getPartnerNickName(User user, ChatRoom chatRoom) {
        String partnerNickName = "";
        User partner = null;

        if (user.getId() == chatRoom.getBuyer().getId()) {
            partner = userEntityRepository.findById(chatRoom.getSeller().getId())
                    .orElseThrow(
                            () -> new UserException(ErrorCode.CHATROOM_PARTNER_USER_NOT_FOUND));
        } else {
            partner = userEntityRepository.findById(chatRoom.getBuyer().getId())
                    .orElseThrow(
                            () -> new UserException(ErrorCode.CHATROOM_PARTNER_USER_NOT_FOUND));
        }

        partnerNickName = partner.getNickName();

        return partnerNickName;
    }
}

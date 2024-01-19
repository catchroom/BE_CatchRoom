package com.example.catchroom_be.domain.chatroom.service;

import com.example.catchroom_be.domain.accommodation.entity.Accommodation;
import com.example.catchroom_be.domain.chatroom.dto.request.ChatRoomCreateRequest;
import com.example.catchroom_be.domain.chatroom.dto.response.ChatRoomCreateResponse;
import com.example.catchroom_be.domain.chatroom.dto.response.ChatRoomInfoResponse;
import com.example.catchroom_be.domain.chatroom.dto.response.ChatRoomListGetResponse;
import com.example.catchroom_be.domain.chatroom.entity.ChatRoom;
import com.example.catchroom_be.domain.chatroom.exception.ChatRoomException;
import com.example.catchroom_be.domain.chatroom.repository.ChatRoomRepository;
import com.example.catchroom_be.domain.chatroom.type.ChatRoomState;
import com.example.catchroom_be.domain.product.entity.Product;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.global.exception.SuccessMessage;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {
    private final ProductRepository productRepository;

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

    @Transactional
    public ChatRoomCreateResponse createChatRoom(ChatRoomCreateRequest chatRoomCreateRequest) {
        ChatRoom chatRoom = ChatRoom.create(
            userEntityRepository.getReferenceById(chatRoomCreateRequest.getSellerId()),
            userEntityRepository.getReferenceById(chatRoomCreateRequest.getBuyerId()),
            productRepository.getReferenceById(chatRoomCreateRequest.getProductId())
        );

        chatRoomRepository.save(chatRoom);
        return ChatRoomCreateResponse.fromEntity(chatRoom);
    }

    @Transactional(readOnly = true)
    public List<ChatRoomListGetResponse> findChatRoomListByMemberId(User user) {
        List<ChatRoom> ChatRoomListUserIsBuyer = chatRoomRepository.findAllByBuyerIdOrSellerId(
                user.getId(),user.getId()
        );
        List<ChatRoom> chatRooms = new ArrayList<>();

        for (ChatRoom chatRoom : ChatRoomListUserIsBuyer) {
            if (chatRoom.getBuyer().equals(user) && chatRoom.getBuyerState().equals(ChatRoomState.DONT_SEE)) {
                continue;
            } else if (chatRoom.getSeller().equals(user) && chatRoom.getSellerState().equals(ChatRoomState.DONT_SEE)) {
                continue;
            }
            chatRoom.updateUserIdentity(user.getId());
            chatRooms.add(chatRoom);
        }
        return chatRooms.stream()
            .map(ChatRoomListGetResponse::fromEntity)
            .collect(Collectors.toList());
    }

    private String getPartnerNickName(User user, ChatRoom chatRoom) {
        String partnerNickName = "";
        User partner = null;

        if (user.getId() == chatRoom.getBuyer().getId()) {
            partner = userEntityRepository.findById(chatRoom.getSeller().getId())
                    .orElseThrow(
                            () -> new UserException(ErrorCode.CHATROOM_PARTNER_USER_NOT_FOUND));
        } else if (user.getId() == chatRoom.getSeller().getId()){
            partner = userEntityRepository.findById(chatRoom.getBuyer().getId())
                    .orElseThrow(
                            () -> new UserException(ErrorCode.CHATROOM_PARTNER_USER_NOT_FOUND));
        } else {
            throw new ChatRoomException(ErrorCode.CHATROOM_USER_NOT_FOUND);
        }

        partnerNickName = partner.getNickName();

        return partnerNickName;
    }

    @Transactional
    public SuccessMessage deleteChatRoom(User user, String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomNumber(roomId)
                .orElseThrow(() -> new ChatRoomException(ErrorCode.CHATROOM_NOT_FOUND));

        if (chatRoom.getSeller().equals(user)) {
            chatRoom.updateSellerState(ChatRoomState.DONT_SEE);
        } else if (chatRoom.getBuyer().equals(user)) {
            chatRoom.updateBuyerState(ChatRoomState.DONT_SEE);
        }

        ChatRoom saveChatRoom = chatRoomRepository.save(chatRoom);
        if (saveChatRoom == null) return SuccessMessage.createSuccessMessage("FAILED");
        return SuccessMessage.createSuccessMessage("SUCCESS");
    }
}

package com.example.catchroom_be.domain.chatroom.service;

import com.example.catchroom_be.domain.chatroom.dto.request.ChatRoomCreateRequest;
import com.example.catchroom_be.domain.chatroom.dto.response.ChatRoomCreateResponse;
import com.example.catchroom_be.domain.chatroom.dto.response.ChatRoomListGetResponse;
import com.example.catchroom_be.domain.chatroom.entity.ChatRoom;
import com.example.catchroom_be.domain.chatroom.exception.ChatRoomException;
import com.example.catchroom_be.domain.chatroom.repository.ChatRoomRepository;
import com.example.catchroom_be.domain.chatroom.type.ChatRoomState;
import com.example.catchroom_be.domain.product.repository.ProductRepository;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.domain.user.exception.UserException;
import com.example.catchroom_be.domain.user.repository.UserEntityRepository;
import com.example.catchroom_be.global.exception.ErrorCode;
import com.example.catchroom_be.global.exception.SuccessMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @Transactional
    public ChatRoomListGetResponse getChatRoomInfo(String roomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomNumber(roomId)
                .orElseThrow(() -> new ChatRoomException(ErrorCode.CHATROOM_NOT_FOUND));

        chatRoom.updateUserIdentity(userId);

        return ChatRoomListGetResponse.fromEntity(chatRoom);

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
            if (chatRoom.getBuyer().getId().equals(user.getId()) && chatRoom.getBuyerState().equals(ChatRoomState.DONT_SEE)) {
                continue;
            } else if (chatRoom.getSeller().getId().equals(user.getId()) && chatRoom.getSellerState().equals(ChatRoomState.DONT_SEE)) {
                continue;
            }
            chatRoom.updateUserIdentity(user.getId());
            chatRooms.add(chatRoom);
        }

        return chatRooms.stream()
            .map(ChatRoomListGetResponse::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public SuccessMessage deleteChatRoom(User user, String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomNumber(roomId)
                .orElseThrow(() -> new ChatRoomException(ErrorCode.CHATROOM_NOT_FOUND));

        StringBuilder logSb = new StringBuilder();

        if (chatRoom.getSeller().getId().equals(user.getId())) {
            chatRoom.updateSellerState(ChatRoomState.DONT_SEE);
            logSb.append("this is seller -> ");
        } else if (chatRoom.getBuyer().getId().equals(user.getId())) {
            chatRoom.updateBuyerState(ChatRoomState.DONT_SEE);
            logSb.append("this is buyer -> ");
        }

        ChatRoom saveChatRoom = chatRoomRepository.save(chatRoom);

        logSb.append(roomId + " : " + user.getId() + " delete");

        if (saveChatRoom == null)
            return SuccessMessage.createSuccessMessage(logSb.toString() + " FAILED");

        return SuccessMessage.createSuccessMessage(logSb.toString() + " SUCCESS");
    }
}

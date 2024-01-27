package com.example.catchroom_be.domain.chatroom.repository;

import com.example.catchroom_be.domain.chatroom.entity.ChatRoom;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepositoryCustom {
    List<String> findUniqueChatRoom(Long loginUserId, Long sellerId, Long productId);

    List<ChatRoom> findChatRoomList(Long buyerId, Long sellerId, Long productId);

}

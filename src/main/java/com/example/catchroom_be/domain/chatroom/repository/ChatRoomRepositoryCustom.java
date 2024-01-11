package com.example.catchroom_be.domain.chatroom.repository;

import java.util.List;

public interface ChatRoomRepositoryCustom {
    List<String> findUniqueChatRoom(Long loginUserId, Long sellerId, Long productId);
}

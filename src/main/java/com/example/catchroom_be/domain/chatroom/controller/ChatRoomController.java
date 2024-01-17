package com.example.catchroom_be.domain.chatroom.controller;

import com.example.catchroom_be.domain.chatroom.dto.request.ChatRoomCreateRequest;
import com.example.catchroom_be.domain.chatroom.service.ChatRoomService;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/chat/room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /** 채팅방 내부 정보 반환하는 API */
    @GetMapping()
    public ResponseEntity<?> getChatRoomInfo(
            @RequestParam(name = "roomId") final String roomId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(ApiResponse.create(7999,
            chatRoomService.getChatRoomInfo(roomId, user))
        );
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(
            @RequestBody ChatRoomCreateRequest chatRoomCreateRequest
    ) {
        return ResponseEntity.ok(ApiResponse.create(6002,
                chatRoomService.createChatRoom(chatRoomCreateRequest))
        );
    }

}

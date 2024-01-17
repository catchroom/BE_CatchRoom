package com.example.catchroom_be.domain.chatroom.controller;

import com.example.catchroom_be.domain.chatroom.service.ChatRoomInfoService;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/chat")
public class ChatRoomController {

    private final ChatRoomInfoService chatRoomInfoService;

    /** 채팅방 내부 정보 반환하는 API */
    @GetMapping("/room")
    public ResponseEntity<?> getChatRoomInfo(
            @RequestParam(name = "roomId") final String roomId,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(ApiResponse.create(7999,
                chatRoomInfoService.getChatRoomInfo(roomId, user))
        );
    }

}

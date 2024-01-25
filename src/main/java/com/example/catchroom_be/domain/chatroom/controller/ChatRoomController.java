package com.example.catchroom_be.domain.chatroom.controller;

import com.example.catchroom_be.domain.chatroom.dto.request.ChatRoomCreateRequest;
import com.example.catchroom_be.domain.chatroom.dto.response.ChatRoomListGetResponse;
import com.example.catchroom_be.domain.chatroom.service.ChatRoomService;
import com.example.catchroom_be.domain.user.entity.User;
import com.example.catchroom_be.global.common.ApiResponse;
import com.example.catchroom_be.global.exception.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/chat/room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;


    /** 채팅방 내부 정보 반환하는 API */
    @GetMapping("/info")
    public ChatRoomListGetResponse getChatRoomInfoChat(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "roomId") String roomId
    ) {
        return chatRoomService.getChatRoomInfo(user, roomId);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createRoom(
            @RequestBody ChatRoomCreateRequest chatRoomCreateRequest
    ) {
        return ResponseEntity.ok(ApiResponse.create(6002,
                chatRoomService.createChatRoom(chatRoomCreateRequest))
        );
    }
    @GetMapping("/list")
    public ResponseEntity<?> findChatRoomListByMemberId(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
            ApiResponse.create(6000, chatRoomService.findChatRoomListByMemberId(user)));
    }

    @GetMapping("/list/chat")
    public List<ChatRoomListGetResponse> findChatRoomListByMemberIdChat(
            @AuthenticationPrincipal User user
    ) {
        return chatRoomService.findChatRoomListByMemberId(user);
    }


    @DeleteMapping
    public SuccessMessage deleteChatRoom(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "roomId") String roomId
    ) {
        return chatRoomService.deleteChatRoom(user, roomId);
    }

}

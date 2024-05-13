package com.quangta.chatapp.controller;

import com.quangta.chatapp.entity.ChatMessage;
import com.quangta.chatapp.service.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {

    ChatService chatService;

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@RequestBody ChatMessage message) {
        chatService.sendMessage(message);
        return ResponseEntity.ok().build();
    }
}

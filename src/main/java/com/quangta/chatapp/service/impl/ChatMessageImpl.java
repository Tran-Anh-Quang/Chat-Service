package com.quangta.chatapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangta.chatapp.entity.ChatMessage;
import com.quangta.chatapp.repository.ChatMessageRepository;
import com.quangta.chatapp.service.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatMessageImpl implements ChatService {

    ChatMessageRepository chatMessageRepository;

    @NonFinal
    SimpMessagingTemplate messagingTemplate;

    StringRedisTemplate stringRedisTemplate;

    @Override
    public void sendMessage(ChatMessage message) {
        // Save message to database
        message.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(message);

        // Convert message object to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String messageJson = objectMapper.writeValueAsString(message);
            // Publish message to Redis channel
            stringRedisTemplate.convertAndSend("chat", messageJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Send message to recipient via WebSocket
        messagingTemplate.convertAndSendToUser(message.getRecipient().getUsername(), "/topic/chat", message);
    }
}

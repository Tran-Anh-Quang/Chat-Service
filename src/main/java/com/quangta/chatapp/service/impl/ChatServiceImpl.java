package com.quangta.chatapp.service.impl;

import com.quangta.chatapp.entity.ChatMessage;
import com.quangta.chatapp.repository.ChatRepository;
import com.quangta.chatapp.service.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatServiceImpl implements ChatService {
    ChatRepository chatRepository;

    @Override
    public void saveMessage(ChatMessage chatMessage) {
        chatRepository.save(chatMessage);
    }
}

package com.quangta.chatapp.service;

import com.quangta.chatapp.entity.ChatMessage;

public interface ChatService {
    void saveMessage(ChatMessage message);
}

package com.quangta.chatapp.repository;

import com.quangta.chatapp.entity.ChatMessage;
import com.quangta.chatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderAndRecipient(User sender, User recipient);
}


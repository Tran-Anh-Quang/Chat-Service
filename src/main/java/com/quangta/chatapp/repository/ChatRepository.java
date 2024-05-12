package com.quangta.chatapp.repository;

import com.quangta.chatapp.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}

package com.quangta.chatapp.repository;

import com.quangta.chatapp.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id = :userId")
    List<Chat> findChatByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Chat c WHERE c.isGroup = false AND :senderId MEMBER OF c.users AND :recipientId MEMBER OF c.users")
    Chat findChatByUserIds(@Param("senderId") Long senderId, @Param("recipientId") Long recipientId);
}

package com.quangta.chatapp.repository;

import com.quangta.chatapp.entity.ChatMessage;
import com.quangta.chatapp.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRepository {

    static final String REDIS_KEY_PREFIX = "chat:";

    @NonFinal
    RedisTemplate<String, ChatMessage> redisTemplate;

    public void save(ChatMessage chatMessage) {
        String key = REDIS_KEY_PREFIX + chatMessage.getTo();
        redisTemplate.opsForList().leftPush(key, chatMessage);
    }
}


package com.quangta.chatapp.service;

import com.quangta.chatapp.dto.request.GroupChatRequest;
import com.quangta.chatapp.entity.Chat;
import com.quangta.chatapp.entity.User;

public interface ChatService {

    Chat createChat(User senderId, Long recipientId);

    Chat findChatById(Long chatId);

    Chat findAllChatByUserId(Long userId);

    Chat createGroup(GroupChatRequest request, User requestUser);

    Chat addUserToGroup(Long chatId, Long userId, User requestUser);

    Chat renameGroup(Long chatId, String groupName, User requestUser);

    Chat removeUserFromGroup(Long chatId, Long userId, User requestUser);

    void deleteChat(Long chatId, Long requestUserId);
}

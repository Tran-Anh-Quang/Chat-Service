package com.quangta.chatapp.service.impl;

import com.quangta.chatapp.dto.request.GroupChatRequest;
import com.quangta.chatapp.entity.Chat;
import com.quangta.chatapp.entity.User;
import com.quangta.chatapp.exception.ChatErrorCode;
import com.quangta.chatapp.exception.ChatException;
import com.quangta.chatapp.exception.UserErrorCode;
import com.quangta.chatapp.exception.UserException;
import com.quangta.chatapp.repository.ChatRepository;
import com.quangta.chatapp.repository.UserRepository;
import com.quangta.chatapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ChatServiceImpl implements ChatService {
    ChatRepository chatRepository;

    UserRepository userRepository;

    @Override
    public Chat createChat(User senderId, Long recipientId) {
        var recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        var isChatExist = chatRepository.findChatByUserIds(senderId.getId(), recipientId);

        if (isChatExist != null) {
            return isChatExist;
        }

        Chat chat = new Chat();
        chat.setIsGroup(false);
        chat.setCreatedBy(senderId);
        chat.getUsers().add(recipient);
        chat.getUsers().add(senderId);

        return chat;
    }

    @Override
    public Chat findChatById(Long chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException(ChatErrorCode.CHAT_NOT_FOUND));

    }

    @Override
    public Chat findAllChatByUserId(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        List<Chat> chats = chatRepository.findChatByUserId(user.getId());

        return (Chat) chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest request, User requestUserId) {
        Chat group = new Chat();
        group.setIsGroup(true);
        group.setChatImage(request.getChatImage());
        group.setChatName(request.getChatName());
        group.setCreatedBy(requestUserId);

        for (Long userId : request.getUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
            group.getUsers().add(user);
        }

        return group;
    }

    @Override
    public Chat addUserToGroup(Long chatId, Long userId) {
        return null;
    }

    @Override
    public Chat renameGroup(Long chatId, String groupName, Long requestUserId) {
        return null;
    }

    @Override
    public Chat removeUserFromGroup(Long chatId, Long userId, Long requestUserId) {
        return null;
    }

    @Override
    public Chat deleteChat(Long chatId, Long requestUserId) {
        return null;
    }
}

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

        return chatRepository.save(chat);
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
    public Chat createGroup(GroupChatRequest request, User requestUser) {
        Chat group = new Chat();
        group.setIsGroup(true);
        group.setChatImage(request.getChatImage());
        group.setChatName(request.getChatName());
        group.setCreatedBy(requestUser);
        group.getAdmins().add(requestUser);

        for (Long userId : request.getUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
            group.getUsers().add(user);
        }

        return chatRepository.save(group);
    }

    @Override
    public Chat addUserToGroup(Long chatId, Long userId, User requestUser) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        var chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException(ChatErrorCode.CHAT_NOT_FOUND));

        if (!chat.getAdmins().contains(requestUser)) {
            throw new ChatException(ChatErrorCode.USER_NOT_ADMIN);
        }
        chat.getUsers().add(user);

        return chatRepository.save(chat);
    }

    @Override
    public Chat renameGroup(Long chatId, String groupName, User requestUser) {
        var chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException(ChatErrorCode.CHAT_NOT_FOUND));

        if (!chat.getUsers().contains(requestUser)) {
            throw new ChatException(ChatErrorCode.USER_NOT_IN_CHAT);
        }

        chat.setChatName(groupName);
        return chatRepository.save(chat);
    }

    @Override
    public Chat removeUserFromGroup(Long chatId, Long userId, User requestUser) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        var chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException(ChatErrorCode.CHAT_NOT_FOUND));

        if (chat.getAdmins().contains(requestUser)) {
            chat.getUsers().remove(user);
            return chatRepository.save(chat);
        }else if (chat.getUsers().contains(user)) {
            if (user.getId().equals(requestUser.getId())) {
                chat.getUsers().remove(user);
                chatRepository.save(chat);
                return chat;
            }
        }else {
            throw new ChatException(ChatErrorCode.USER_NOT_ADMIN);
        }

        return chat;
    }

    @Override
    public void deleteChat(Long chatId, Long requestUserId) {
        var chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException(ChatErrorCode.CHAT_NOT_FOUND));
        if (chat.getCreatedBy().getId().equals(requestUserId)) {
            chatRepository.delete(chat);
        }
    }
}

package com.quangta.chatapp.service.impl;

import com.quangta.chatapp.dto.request.UpdateUserRequest;
import com.quangta.chatapp.entity.User;
import com.quangta.chatapp.exception.UserErrorCode;
import com.quangta.chatapp.exception.UserException;
import com.quangta.chatapp.repository.UserRepository;
import com.quangta.chatapp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public User updateUser(Long id, UpdateUserRequest request) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND));
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String keyword) {
        return userRepository.searchUser(keyword);
    }
}

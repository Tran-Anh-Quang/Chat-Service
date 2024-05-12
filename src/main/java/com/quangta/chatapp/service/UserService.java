package com.quangta.chatapp.service;

import com.quangta.chatapp.dto.UpdateUserRequest;
import com.quangta.chatapp.entity.User;

import java.util.List;

public interface UserService {

    User findUserById(Long id);

    User updateUser(Long id, UpdateUserRequest request);

    List<User> searchUser(String keyword);
}

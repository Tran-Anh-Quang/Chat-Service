package com.quangta.chatapp.controller;

import com.quangta.chatapp.dto.ApiResponse;
import com.quangta.chatapp.dto.UpdateUserRequest;
import com.quangta.chatapp.entity.User;
import com.quangta.chatapp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PutMapping("/{userId}")
    public ApiResponse<User> updateUser(@PathVariable("userId") Long userId, @RequestBody UpdateUserRequest request) {
        userService.updateUser(userId, request);
        return ApiResponse.<User>builder()
                .message("Update user successfully")
                .build();
    }

    @GetMapping("/{keyword}")
    public ApiResponse<List<User>> searchUser(@PathVariable("keyword") String keyword) {
        userService.searchUser(keyword);
        return ApiResponse.<List<User>>builder().build();
    }
}

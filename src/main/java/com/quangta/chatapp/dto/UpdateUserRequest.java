package com.quangta.chatapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {
    private String fullName;
    private String email;
    private String avatar;
}

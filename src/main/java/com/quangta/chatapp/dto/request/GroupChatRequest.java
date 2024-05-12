package com.quangta.chatapp.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupChatRequest {
    private List<Long> userIds;
    private String chatName;
    private String chatImage;
}

package com.quangta.chatapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatException extends RuntimeException {
    private ChatErrorCode chatErrorCode;
}

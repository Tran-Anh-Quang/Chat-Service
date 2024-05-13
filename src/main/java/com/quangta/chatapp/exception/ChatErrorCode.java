package com.quangta.chatapp.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ChatErrorCode {
    CHAT_NOT_FOUND(1001, "Chat not found", HttpStatus.NOT_FOUND),
    USER_NOT_ADMIN(1002, "User is not admin", HttpStatus.BAD_REQUEST),
    USER_NOT_IN_CHAT(1003, "User is not in the chat", HttpStatus.BAD_REQUEST),
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;
}

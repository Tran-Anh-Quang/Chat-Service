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
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;
}

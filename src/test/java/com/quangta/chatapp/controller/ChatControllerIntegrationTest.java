package com.quangta.chatapp.controller;

import com.quangta.chatapp.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatControllerIntegrationTest {

    private final WebSocketStompClient stompClient;

    public ChatControllerIntegrationTest() {
        this.stompClient = createWebSocketStompClient();
        configureWebSocketStompClient(this.stompClient);
    }

    private WebSocketStompClient createWebSocketStompClient() {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        // Configure the stompClient as needed
        return stompClient;
    }

    private static final int PORT = 8080;

    @BeforeEach
    public void setup() {
        configureWebSocketStompClient(stompClient);
    }

    @Test
    void testChatSendMessage() throws Exception {
        StompSession session = stompClient.connect("ws://localhost:" + PORT + "/websocket", new StompSessionHandlerAdapter() {}).get(1, TimeUnit.SECONDS);
        assertThat(session).isNotNull();

        BlockingQueue<ChatMessage> messages = new LinkedBlockingDeque<>();
        session.subscribe("/user/queue/messages", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                messages.add((ChatMessage) payload);
            }
        });

        ChatMessage message = new ChatMessage();
        message.setFrom("user1");
        message.setTo("user2");
        message.setContent("Hello");

        session.send("/app/chat.sendMessage", message);

        ChatMessage received = messages.poll(1, TimeUnit.SECONDS);
        assertThat(received).isNotNull();
        assertThat(received.getFrom()).isEqualTo("user1");
        assertThat(received.getTo()).isEqualTo("user2");
        assertThat(received.getContent()).isEqualTo("Hello");
    }

    void configureWebSocketStompClient(WebSocketStompClient stompClient) {
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }
}
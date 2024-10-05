package com.example.godgame.websocket.webchat;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MyHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final RedisTemplate<String, ChattingMessage> chattingMessageRedisTemplate;

    public MyHandler(RedisTemplate<String, ChattingMessage> chattingMessageRedisTemplate) {
        this.chattingMessageRedisTemplate = chattingMessageRedisTemplate;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        final String sessionId = session.getId();
        final String enteredMessage = sessionId + "님이 입장하셨습니다.";
        sessions.put(sessionId, session);

        sendMessage(sessionId, new TextMessage(enteredMessage));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        final String sessionId = session.getId();

        // 메시지를 처리하고 Redis에 저장
        ChattingMessage chattingMessage = new ChattingMessage();
        chattingMessage.setChatId(chattingMessageRedisTemplate.opsForValue().increment("chatIdCounter"));
        chattingMessage.setMemberId(123); // 실제 사용자 ID로 설정
        chattingMessage.setNickName("user1"); // 실제 닉네임으로 설정
        chattingMessage.setGameRoomId(100); // 실제 게임 룸 ID로 설정
        chattingMessage.setContent(message.getPayload());
        chattingMessage.setCreatedAt(LocalDateTime.now());

        // Redis에 저장
        chattingMessageRedisTemplate.opsForList().rightPush("chattingMessages : " + chattingMessage.getGameRoomId(), chattingMessage);

        // 다른 세션으로 메시지 브로드캐스트
        sendMessage(sessionId, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        final String sessionId = session.getId();
        final String leaveMessage = sessionId + "님이 떠났습니다.";
        sessions.remove(sessionId);

        sendMessage(sessionId, new TextMessage(leaveMessage));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {}

    private void sendMessage(String sessionId, WebSocketMessage<?> message) {
        sessions.values().forEach(s -> {
            if (!s.getId().equals(sessionId) && s.isOpen()) {
                try {
                    s.sendMessage(message);
                } catch (IOException e) {
                    // 에러 처리
                }
            }
        });
    }
}

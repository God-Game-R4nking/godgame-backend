package com.example.godgame.websocket.webchat;

import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.gameroom.service.GameRoomService;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final RedisTemplate<String, ChattingMessage> chattingMessageRedisTemplate;
    private final MemberService memberService;
    private final GameRoomService gameRoomService;

    @Autowired
    private RedisTemplate<String, String> redisGameRoomTemplate;

    public MyHandler(RedisTemplate<String, ChattingMessage> chattingMessageRedisTemplate, MemberService memberService, GameRoomService gameRoomService) {
        this.chattingMessageRedisTemplate = chattingMessageRedisTemplate;
        this.memberService = memberService;
        this.gameRoomService = gameRoomService;
    }

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId");

        if (userId != null) {
            Member member = memberService.findVerifiedMember(userId); // ID로 Member 조회
            session.getAttributes().put("member", member); // 세션에 Member 저장
            final String sessionId = session.getId();
            sessions.put(sessionId, session);
            String enteredMessage = member.getNickName() + "님이 입장하셨습니다.";
            sendMessage(sessionId, new TextMessage(enteredMessage));
        } else {
            session.close(CloseStatus.POLICY_VIOLATION); // 유효하지 않은 ID일 경우 연결 종료
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        final String sessionId = session.getId();

        // 세션에서 사용자 정보를 가져옵니다.
        Member member = (Member) session.getAttributes().get("member");

        if (member != null) {
            // 메시지를 처리하고 Redis에 저장
            ChattingMessage chattingMessage = new ChattingMessage();
            chattingMessage.setChatId(chattingMessageRedisTemplate.opsForValue().increment("chatIdCounter"));
            chattingMessage.setMemberId(member.getMemberId()); // 실제 사용자 ID로 설정
            chattingMessage.setNickName(member.getNickName()); // 실제 닉네임으로 설정
            chattingMessage.setGameRoomId(getGameRoomIdByMemberId(member.getMemberId())); // 실제 게임 룸 ID로 설정
            chattingMessage.setContent(message.getPayload());
            chattingMessage.setCreatedAt(LocalDateTime.now());

            // Redis에 저장
            chattingMessageRedisTemplate.opsForList().rightPush("chattingMessages:" + chattingMessage.getGameRoomId(), chattingMessage);

            // 다른 세션으로 메시지 브로드캐스트
            sendMessage(sessionId, message);
        } else {
            // 사용자 정보가 없는 경우 적절한 에러 처리
            session.sendMessage(new TextMessage("사용자 정보가 없습니다. 다시 로그인하세요."));
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        final String sessionId = session.getId();
        sessions.remove(sessionId);
        Member member = (Member) session.getAttributes().get("member"); // 세션에서 사용자 정보 가져오기
        String leaveMessage = member != null ? member.getNickName() + "님이 떠났습니다." : sessionId + "님이 떠났습니다.";
        sendMessage(sessionId, new TextMessage(leaveMessage));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    }

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


    // memberId로 해당하는 게임룸 ID를 가져오는 메서드
    private Long getGameRoomIdByMemberId(Long memberId) {

        // 게임룸의 키 패턴 (예: "gameRoom:*")
        String gameRoomPattern = "gameRoom:*";

        // Redis에서 모든 게임룸 키를 가져옵니다.
        Set<String> gameRoomKeys = redisGameRoomTemplate.keys(gameRoomPattern);

        if (gameRoomKeys != null) {
            for (String gameRoomKey : gameRoomKeys) {
                // 각 게임룸의 정보를 가져옵니다.
                String gameRoomJson = redisGameRoomTemplate.opsForValue().get(gameRoomKey);
                if (gameRoomJson != null) {
                    try {
                        // JSON을 GameRoom 객체로 변환합니다.
                        GameRoom gameRoom = new ObjectMapper().readValue(gameRoomJson, GameRoom.class);

                        // memberIds에 memberId가 포함되어 있는지 확인합니다.
                        if (gameRoom.getMemberIds().contains(memberId)) {
                            return gameRoom.getGameRoomId(); // 해당 게임룸 ID 반환
                        }
                    } catch (IOException e) {
                        // JSON 파싱 오류 처리
                        e.printStackTrace();
                    }
                }
            }
        }
        return null; // 해당하는 게임룸이 없을 경우 null 반환
    }
}

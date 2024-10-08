package com.example.godgame.websocket.webchat;

import com.example.godgame.chat.entity.Chat;
import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.gameroom.service.GameRoomService;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MyHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, ChattingMessage> redisTemplate;
    private final RedisMessageListenerContainer redisMessageListener;
    private final MemberService memberService;
    private final GameRoomService gameRoomService;
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public MyHandler(ObjectMapper objectMapper, RedisTemplate<String, ChattingMessage> redisTemplate,
                     RedisMessageListenerContainer redisMessageListener, MemberService memberService,
                     GameRoomService gameRoomService) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.redisMessageListener = redisMessageListener;
        this.memberService = memberService;
        this.gameRoomService = gameRoomService;
    }

    @Autowired
    private RedisTemplate<String,String> redisGameRoomTemplate;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            Member member = memberService.findVerifiedMember(userId);
            session.getAttributes().put("member", member);
            sessions.put(session.getId(), session);

            Long gameRoomId = getGameRoomIdByMemberId(member.getMemberId());
            if (gameRoomId != null) {
                subscribeToGameRoom(session, gameRoomId);
                String enteredMessage = member.getNickName() + "님이 입장하셨습니다.";
                publishToGameRoom(gameRoomId, enteredMessage);
            }
        } else {
            session.close(CloseStatus.POLICY_VIOLATION);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Member member = (Member) session.getAttributes().get("member");
        if (member != null) {
            Long gameRoomId = getGameRoomIdByMemberId(member.getMemberId());
            if (gameRoomId != null) {
                ChattingMessage chattingMessage = new ChattingMessage();
                chattingMessage.setChatId(redisTemplate.opsForValue().increment("chatIdCounter"));
                chattingMessage.setMemberId(member.getMemberId());
                chattingMessage.setNickName(member.getNickName());
                chattingMessage.setGameRoomId(gameRoomId);
                chattingMessage.setContent(message.getPayload());
                chattingMessage.setCreatedAt(LocalDateTime.now());

                publishToGameRoom(gameRoomId, objectMapper.writeValueAsString(chattingMessage));
            }
        } else {
            session.sendMessage(new TextMessage("사용자 정보가 없습니다. 다시 접속하세요."));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Member member = (Member) session.getAttributes().get("member");
        sessions.remove(session.getId());
        if (member != null) {
            Long gameRoomId = getGameRoomIdByMemberId(member.getMemberId());
            if (gameRoomId != null) {
                unsubscribeFromGameRoom(session, gameRoomId);
                String leaveMessage = member.getNickName() + "님이 떠났습니다.";
                publishToGameRoom(gameRoomId, leaveMessage);
            }
        }
    }

    private void subscribeToGameRoom(WebSocketSession session, Long gameRoomId) {
        ChannelTopic topic = new ChannelTopic("gameRoom:" + gameRoomId);
        redisMessageListener.addMessageListener((message, pattern) -> {
            try {
                session.sendMessage(new TextMessage(message.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, topic);
    }

    private void unsubscribeFromGameRoom(WebSocketSession session, Long gameRoomId) {
        ChannelTopic topic = new ChannelTopic("gameRoom:" + gameRoomId);
        redisMessageListener.removeMessageListener(null, topic);
    }

    private void publishToGameRoom(Long gameRoomId, String message) {
        redisTemplate.convertAndSend("gameRoom:" + gameRoomId, message);
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

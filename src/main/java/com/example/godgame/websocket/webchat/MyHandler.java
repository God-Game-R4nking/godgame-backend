package com.example.godgame.websocket.webchat;

import com.example.godgame.gameroom.service.GameRoomService;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
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
import java.util.Optional;

public class MyHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final RedisTemplate<String, ChattingMessage> chattingMessageRedisTemplate;
    private final MemberService memberService;
    private final GameRoomService gameRoomService;

    public MyHandler(RedisTemplate<String, ChattingMessage> chattingMessageRedisTemplate, MemberService memberService, GameRoomService gameRoomService) {
        this.chattingMessageRedisTemplate = chattingMessageRedisTemplate;
        this.memberService = memberService;
        this.gameRoomService = gameRoomService;
    }

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = session.getUri().getQuery();
        String userId = null;

        // 쿼리 파라미터에서 userId 추출
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if ("userId".equals(keyValue[0])) {
                    userId = keyValue[1]; // userId 값 할당
                }
            }
        }

        if (userId != null) {
            Member member = memberService.findVerifiedMember(userId); // ID로 Member 조회
            if (member != null) {
                session.getAttributes().put("member", member); // 세션에 Member 저장
                final String sessionId = session.getId();
                sessions.put(sessionId, session);
                String enteredMessage = member.getNickName() + "님이 입장하셨습니다.";
                sendMessage(sessionId, new TextMessage(enteredMessage));
            } else {
                session.close(CloseStatus.POLICY_VIOLATION); // 유효하지 않은 ID일 경우 연결 종료
            }
        } else {
            session.close(CloseStatus.POLICY_VIOLATION); // userId가 없는 경우 연결 종료
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
        String gameRoomKey = "member:" + memberId + ":gameRoom";
        // ListOperations을 사용하여 리스트 가져오기
        ListOperations<String, String> listOps = stringRedisTemplate.opsForList();
        List<String> gameRoomIds = listOps.range(gameRoomKey, 0, -1); // 전체 리스트 가져오기

        if (gameRoomIds == null || gameRoomIds.isEmpty()) {
            return null; // 게임룸 ID가 없을 경우
        }

        // 각 게임룸 ID를 확인하여 memberId와 매칭되는 것을 찾아 반환
        for (String gameRoomId : gameRoomIds) {
            if (isMemberInGameRoom(memberId, gameRoomId)) { // memberId가 게임룸에 포함되어 있는지 확인
                return Long.parseLong(gameRoomId); // 매칭되는 게임룸 ID 반환
            }
        }

        return null; // 매칭되는 게임룸 ID가 없을 경우
    }

    // memberId가 게임룸에 포함되어 있는지 확인하는 메서드 (예시)
    private boolean isMemberInGameRoom(Long memberId, String gameRoomId) {
        // 이 메서드에서 gameRoomId에 해당하는 멤버 ID를 확인하는 로직을 구현
        // 예를 들어 Redis에서 멤버 리스트를 가져와서 확인하는 방식
        String memberKey = "gameRoom:" + gameRoomId + ":members"; // 예시 키
        List<String> members = stringRedisTemplate.opsForList().range(memberKey, 0, -1);
        return members != null && members.contains(String.valueOf(memberId));
    }
}

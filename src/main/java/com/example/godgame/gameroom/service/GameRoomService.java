package com.example.godgame.gameroom.service;

import com.example.godgame.chat.service.ChatService;
import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.history.entity.GameHistory;
import com.example.godgame.history.entity.GameRoomHistory;
import com.example.godgame.history.repository.GameHistoryRepository;
import com.example.godgame.history.repository.GameRoomHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Service
public class GameRoomService {

    @Autowired
    private RedisTemplate<String, String> redisGameRoomTemplate;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private GameHistoryRepository gameHistoryRepository;

    @Autowired
    private GameRoomHistoryRepository gameRoomHistoryRepository;

    @Autowired
    private ChatService chatService; // ChatService 추가



    public void createGameRoom(GameRoom gameRoom) {
        // ID 카운터 초기화 및 증가
        Long gameRoomId = redisGameRoomTemplate.opsForValue().increment("gameRoomIdCounter");
        if (gameRoomId == null) {
            throw new RuntimeException("Failed to generate game room ID");
        }

        // Redis에서 해당 멤버의 게임룸 확인
        String existingGameRoomKey = "member:" + gameRoom.getMemberIds().get(0) + ":gameRoom";
        String existingRoomId = redisGameRoomTemplate.opsForValue().get(existingGameRoomKey);

        if (existingRoomId != null) {
            // 기존 게임룸이 존재하면 그 게임룸이 비어있는지 확인
            String existingGameRoomJson = redisGameRoomTemplate.opsForValue().get("gameRoom:" + existingRoomId);
            GameRoom existingGameRoom = convertFromJson(existingGameRoomJson);

            if (existingGameRoom != null && existingGameRoom.getCurrentPopulation() > 0) {
                throw new RuntimeException("사용자는 이미 게임룸을 생성했습니다. 하나의 게임룸만 생성할 수 있습니다.");
            } else {
                // 비어있는 경우 기존 게임룸 삭제
                removeGameRoomIfEmpty("gameRoom:" + existingRoomId);
            }
        }

        // 게임룸 객체에 ID 설정
        gameRoom.setGameRoomId(gameRoomId);

        // JSON으로 변환
        String jsonGameRoom = convertToFormattedJson(gameRoom);
        System.out.println("Saving GameRoom to Redis: " + jsonGameRoom);

        // 멤버가 방을 생성했다는 정보도 저장
        redisGameRoomTemplate.opsForValue().set(existingGameRoomKey, String.valueOf(gameRoomId));

        // Redis에 게임룸 저장 (JSON 문자열)
        redisGameRoomTemplate.opsForValue().set("gameRoom:" + gameRoomId, jsonGameRoom);

        // 게임룸 히스토리 생성 및 DB 저장
        GameRoomHistory gameRoomHistory = new GameRoomHistory();
        gameRoomHistory.setCurrentMember(gameRoom.getCurrentPopulation());
        gameRoomHistory.setCreatedAt(LocalDateTime.now());
        gameRoomHistory.setModifiedAt(LocalDateTime.now());
        gameRoomHistoryRepository.save(gameRoomHistory);

        System.out.println("Game room created with ID: " + gameRoomId);
    }

    public void startGame(long gameRoomId) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get("gameRoom:" + gameRoomId);
        GameRoom gameRoom = convertFromJson(gameRoomJson); // JSON에서 역직렬화
        if (gameRoom != null && "대기중".equals(gameRoom.getGameRoomStatus())) {
            gameRoom.setGameRoomStatus("게임중");
            redisGameRoomTemplate.opsForValue().set("gameRoom:" + gameRoomId, convertToFormattedJson(gameRoom)); // JSON으로 업데이트

            GameRoomHistory gameRoomHistory = new GameRoomHistory();
            gameRoomHistory.setCurrentMember(gameRoom.getCurrentPopulation());
            gameRoomHistory.setCreatedAt(LocalDateTime.now());
            gameRoomHistory.setModifiedAt(LocalDateTime.now());

            try {
                gameRoomHistoryRepository.save(gameRoomHistory); // DB에 저장
            } catch (Exception e) {
                // 예외 처리
                e.printStackTrace();
            }
        }
    }


    public boolean joinGame(long gameRoomId, Long memberId) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get("gameRoom:" + gameRoomId);
        GameRoom gameRoom = convertFromJson(gameRoomJson); // JSON에서 역직렬화

        // 게임 방이 존재하지 않거나 게임이 시작된 경우
        if (gameRoom == null || "게임중".equals(gameRoom.getGameRoomStatus())) {
            return false; // 게임이 시작된 경우 신청 불가
        }

        // 최대 인원수 초과시 입장 실패
        if (gameRoom.getCurrentPopulation() >= gameRoom.getMaxPopulation()) {
            return false; // 최대 인원수 초과시에 입장 실패
        }

        // 이미 존재하는 멤버 아이디일 경우 입장 실패
        if (gameRoom.getMemberIds().contains(memberId)) {
            return false; // 이미 존재하는 멤버 아이디일 경우 입장 실패
        }

        // 멤버를 추가하고 현재 인원 수를 증가시킴
        gameRoom.getMemberIds().add(memberId);
        gameRoom.setCurrentPopulation(gameRoom.getCurrentPopulation() + 1); // 인원수 증가

        // 게임룸을 다시 Redis에 저장
        String updatedJsonGameRoom = convertToFormattedJson(gameRoom);
        redisGameRoomTemplate.opsForValue().set("gameRoom:" + gameRoomId, updatedJsonGameRoom);

        return true; // 성공적으로 입장함
    }


    public boolean leaveGame(long gameRoomId, Long memberId) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get("gameRoom:" + gameRoomId);
        GameRoom gameRoom = convertFromJson(gameRoomJson);

        // 게임 방이 존재하지 않거나 멤버가 존재하지 않는 경우
        if (gameRoom == null || !gameRoom.getMemberIds().contains(memberId)) {
            return false; // 게임 방이 없거나 해당 멤버가 없음
        }

        // 멤버 제거
        boolean removed = gameRoom.removeMember(memberId);
        if (removed) {
            // 인원수 업데이트
            int newPopulation = gameRoom.getCurrentPopulation() - 1;
            gameRoom.setCurrentPopulation(newPopulation);
            System.out.println("Updated population: " + newPopulation); // 로그 추가

            // 게임룸을 다시 Redis에 저장
            redisGameRoomTemplate.opsForValue().set("gameRoom:" + gameRoomId, convertToFormattedJson(gameRoom));

            // 방이 비었으면 삭제
            String strGameRoomId = "gameRoom:" + gameRoomId;
            removeGameRoomIfEmpty(strGameRoomId); // gameRoomId로 직접 삭제

            // 멤버의 게임룸 정보를 삭제
            String existingGameRoomKey = "member:" + memberId + ":gameRoom";
            redisGameRoomTemplate.delete(existingGameRoomKey); // 기존 게임룸 ID 삭제

            return true; // 성공적으로 나감
        }

        return false; // 멤버 제거 실패
    }

    public void removeGameRoomIfEmpty(String gameRoomId) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get(gameRoomId);
        GameRoom gameRoom = convertFromJson(gameRoomJson); // JSON 문자열을 GameRoom 객체로 변환
        System.out.println("Game room JSON retrieved from Redis: " + gameRoomJson); // 로그 추가

        if (gameRoomJson == null) {
            System.out.println("Game room JSON is null for ID: " + gameRoomId);
            return;
        }

        if (gameRoom != null) {
            if (gameRoom.getCurrentPopulation() == 0) {
                redisGameRoomTemplate.delete(gameRoomId); // 방 삭제
                System.out.println("Game room deleted: " + gameRoomId);
            } else {
                System.out.println("Game room not empty: " + gameRoomId + ", current population: " + gameRoom.getCurrentPopulation());
            }
        } else {
            System.out.println("Game room not found: " + gameRoomId);
        }
    }

    public void endGame(String roomName, Map<Long, Integer> scores) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get(roomName);
        GameRoom gameRoom = convertFromJson(gameRoomJson);
        if (gameRoom != null && "게임중".equals(gameRoom.getGameRoomStatus())) {
            gameRoom.setGameRoomStatus("대기중");
            redisGameRoomTemplate.opsForValue().set(roomName, convertToFormattedJson(gameRoom)); // 상태 업데이트

            // 각 멤버의 게임 히스토리 저장
            for (Long memberId : gameRoom.getMemberIds()) {
                GameHistory gameHistory = new GameHistory();
                gameHistory.setMemberId(memberId);
                gameHistory.setGameId(gameRoom.getGameId());
                gameHistory.setScore(scores.getOrDefault(memberId, 0)); // 점수 가져오기, 없으면 0
                gameHistory.setCreatedAt(LocalDateTime.now());
                gameHistory.setModifiedAt(LocalDateTime.now());
                gameHistoryRepository.save(gameHistory);
            }
        }
        // 게임 종료 시 Redis에 저장된 모든 채팅 메시지를 MongoDB에 저장
        chatService.saveAllChatsFromRedis();
    }

    public GameRoom getGameRoom(String gameRoomName) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get(gameRoomName);
        return convertFromJson(gameRoomJson); // JSON에서 역직렬화
    }

    public long generateGameRoomId() {
        return redisGameRoomTemplate.opsForValue().increment("gameRoomIdCounter"); // "gameRoomIdCounter" 키를 사용하여 자동 증가
    }

    // JSON으로 직렬화 하면서
    //"gameRoomStatus\":\"\xeb\x8c\x80\xea\xb8\xb0\xec\xa4\x91\ 이런 타입의 포맷도 역시 변환해줌
    // UTF-8 형식으로 저장해줌
    public String convertToFormattedJson(GameRoom gameRoom) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(gameRoom);
            System.out.println("Converted JSON: " + jsonString); // JSON 출력
            return jsonString;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert GameRoom to JSON", e);
        }
    }

    private GameRoom convertFromJson(String json) {
        if (json == null) {
            throw new IllegalArgumentException("Input JSON string cannot be null");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, GameRoom.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting JSON to GameRoom", e);
        }
    }

    public GameRoom findGameRoomByMemberId(Long memberId) {
        // Redis에서 모든 게임룸의 ID를 가져옵니다.
        Set<String> allGameRoomKeys = redisGameRoomTemplate.keys("gameRoom:*");
        if (allGameRoomKeys != null) {
            for (String key : allGameRoomKeys) {
                String gameRoomJson = redisGameRoomTemplate.opsForValue().get(key);
                GameRoom gameRoom = convertFromJson(gameRoomJson);
                if (gameRoom != null && gameRoom.getMemberIds().contains(memberId)) {
                    return gameRoom; // 해당 memberId가 포함된 GameRoom 반환
                }
            }
        }
        return null; // 찾지 못한 경우 null 반환
    }

}

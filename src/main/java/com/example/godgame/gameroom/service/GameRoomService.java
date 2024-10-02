package com.example.godgame.gameroom.service;

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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

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



    public void createGameRoom(GameRoom gameRoom) {
        // ID 카운터 초기화 및 증가
        Long gameRoomId = redisGameRoomTemplate.opsForValue().increment("gameRoomIdCounter");
        if (gameRoomId == null) {
            throw new RuntimeException("Failed to generate game room ID");
        }

        // Redis에 해당 멤버의 기존 게임룸 확인
        String existingGameRoomKey = "member:" + gameRoom.getMemberIds().get(0) + ":gameRoom";
        if (redisGameRoomTemplate.opsForValue().get(existingGameRoomKey) != null) {
            throw new RuntimeException("사용자는 이미 게임룸을 생성했습니다. 하나의 게임룸만 생성할 수 있습니다.");
        }

        // 게임룸 객체에 ID 설정
        gameRoom.setGameRoomId(gameRoomId);

        // JSON으로 변환
        String jsonGameRoom = convertToFormattedJson(gameRoom);
        System.out.println("Saving GameRoom to Redis: " + jsonGameRoom);

        // 멤버가 방을 생성했다는 정보도 저장
        redisGameRoomTemplate.opsForValue().set(existingGameRoomKey, String.valueOf(gameRoomId));

        // Redis에 게임룸 저장 (JSON 문자열)
        redisGameRoomTemplate.opsForValue().set("gameRoom:" + gameRoom.getGameRoomId(), jsonGameRoom);


        // 게임룸 히스토리 생성 및 DB 저장
        GameRoomHistory gameRoomHistory = new GameRoomHistory();
        gameRoomHistory.setCurrentMember(1);
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

        if (gameRoom == null || "게임중".equals(gameRoom.getGameRoomStatus())) {
            return false; // 게임이 시작된 경우 신청 불가
        }

        if (gameRoom.getCurrentPopulation() >= gameRoom.getMaxPopulation()) {
            return false; // 최대 인원수 초과시에 입장 실패
        }

        // 현재 방에 들어온 memberId의 리스트에 같은 멤버 아이디가 있는지 확인
        if (gameRoom.getMemberIds().contains(memberId)) {
            return false; // 이미 존재하는 멤버 아이디일 경우 입장 실패
        }

        // 멤버를 추가하고 현재 인원 수를 증가시킴
        gameRoom.getMemberIds().add(memberId);
        gameRoom.setCurrentPopulation(gameRoom.getCurrentPopulation() + 1);

        // 게임룸을 다시 Redis에 저장
        String updatedJsonGameRoom = convertToFormattedJson(gameRoom);
        redisGameRoomTemplate.opsForValue().set("gameRoom:" + gameRoomId, updatedJsonGameRoom);

        return true; // 성공적으로 입장함
    }

    public void leaveGame(String roomName, Long memberId) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get(roomName);
        GameRoom gameRoom = convertFromJson(gameRoomJson);
        if (gameRoom != null) {
            boolean removed = gameRoom.removeMember(memberId); // 멤버 제거
            if (removed) {
                // 인원수 업데이트
                gameRoom.setCurrentPopulation(gameRoom.getCurrentPopulation() - 1);
                // memberIds 리스트에서 멤버 ID 제거
                gameRoom.getMemberIds().remove(memberId);

                redisGameRoomTemplate.opsForValue().set(roomName, convertToFormattedJson(gameRoom)); // JSON으로 업데이트
                removeGameRoomIfEmpty(roomName); // 방이 비었으면 삭제
            }
        }
    }

    public void removeGameRoomIfEmpty(String roomName) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get(roomName);
        GameRoom gameRoom = convertFromJson(gameRoomJson);
        if (gameRoom != null && gameRoom.getCurrentPopulation() == 0) {
            redisGameRoomTemplate.delete(roomName); // 방 삭제
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
        try {
            // JSON 문자열이 UTF-8로 인코딩되어 있는지 확인
            String decodedJson = URLDecoder.decode(json, StandardCharsets.UTF_8.name());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(decodedJson, GameRoom.class);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error while decoding JSON", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting JSON to GameRoom", e);
        }
    }


}

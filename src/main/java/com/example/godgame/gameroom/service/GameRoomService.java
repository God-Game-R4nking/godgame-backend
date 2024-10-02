package com.example.godgame.gameroom.service;

import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.history.entity.GameHistory;
import com.example.godgame.history.entity.GameRoomHistory;
import com.example.godgame.history.repository.GameHistoryRepository;
import com.example.godgame.history.repository.GameRoomHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class GameRoomService {
        @Autowired
        private RedisTemplate<String, Object> redisTemplate;

        @Autowired
        private ApplicationEventPublisher eventPublisher;

        @Autowired
        private GameHistoryRepository gameHistoryRepository;

    @Autowired
    private GameRoomHistoryRepository gameRoomHistoryRepository;

    public void createGameRoom(String gameRoomName, String password, boolean isPublic, long memberId, long gameId) {
        // 게임룸 ID 카운터가 존재하지 않으면 초기화
        if (redisTemplate.opsForValue().get("gameRoomIdCounter") == null) {
            redisTemplate.opsForValue().set("gameRoomIdCounter", 0);
        }
        // 해당 사용자가 게임룸을 만들었는지 검증하는 메서드
        String existingGameRoomKey = "gameRoom:" + gameId;
        if (redisTemplate.opsForValue().get(existingGameRoomKey) != null) {
            throw new RuntimeException("사용자는 이미 게임룸을 생성했습니다. 하나의 게임룸만 생성할 수 있습니다.");
        }

        //  게임룸 ID를 자동으로 증가시킴
        Long gameRoomId = redisTemplate.opsForValue().increment("gameRoomIdCounter");

        // 실패시 예외 처리
        if (gameRoomId == null) {
            throw new RuntimeException("Failed to generate game room ID");
        }

        // 새로운 게임룸 객체 생성 (ID를 포함)
        GameRoom gameRoom = new GameRoom(gameRoomId, gameRoomName, password, isPublic ? "비공개" : "공개", memberId, gameId);

        // Redis에 게임룸 저장 (ID를 키로 사용)
        // 나중에 ID로 사용할땐 섭스트링으로 gameRoom: 이걸빼고 long 타입으로 바꿔야해서 파싱을 2번해서 써야함
        redisTemplate.opsForValue().set("gameRoom:" + gameRoomId, gameRoom);

        // 게임룸 히스토리 생성 및 DB 저장
        GameRoomHistory gameRoomHistory = new GameRoomHistory();
        gameRoomHistory.setCurrentMember(1); // 초기 인원수
        gameRoomHistory.setCreatedAt(LocalDateTime.now());
        gameRoomHistory.setModifiedAt(LocalDateTime.now());
        gameRoomHistoryRepository.save(gameRoomHistory); // DB에 저장

        System.out.println("Game room created with ID: " + gameRoomId);
    }

    public void startGame(long gameRoomId) {
        GameRoom gameRoom = (GameRoom) redisTemplate.opsForValue().get(String.valueOf(gameRoomId)); // ID를 문자열로 변환
        if (gameRoom != null && "대기중".equals(gameRoom.getGameRoomStatus())) {
            gameRoom.setGameRoomStatus("게임중");
            redisTemplate.opsForValue().set(String.valueOf(gameRoomId), gameRoom); // ID를 문자열로 변환하여 업데이트

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
        GameRoom gameRoom = (GameRoom) redisTemplate.opsForValue().get("gameRoom:" + gameRoomId); // 키 수정
        if (gameRoom == null || "게임중".equals(gameRoom.getGameRoomStatus())) {
            return false; // 게임이 시작된 경우 신청 불가
        }

        if (gameRoom.getCurrentPopulation() >= gameRoom.getMaxPopulation()) {
            return false; // 최대 인원수 초과시에 입장 실패
        }

        // 멤버 추가 및 인원 수 업데이트
        boolean added = gameRoom.getMemberIds().add(memberId); // 해당 멤버를 레디스 게임룰 객체 리스트에 추가
        if (added) {
            gameRoom.setCurrentPopulation(gameRoom.getCurrentPopulation() + 1); // 인원수 업데이트
            // Redis에 업데이트
            redisTemplate.opsForValue().set("gameRoom:" + gameRoomId, gameRoom);
        }

        return added; // 신청 성공 여부 반환
    }

    public void leaveGame(String roomName, Long memberId) {
        GameRoom gameRoom = (GameRoom) redisTemplate.opsForValue().get(roomName);
        if (gameRoom != null) {
            boolean removed = gameRoom.removeMember(memberId); // 멤버 제거
            if (removed) {
                // 인원수 업데이트
                gameRoom.setCurrentPopulation(gameRoom.getCurrentPopulation() - 1);
                // memberIds 리스트에서 멤버 ID 제거
                gameRoom.getMemberIds().remove(memberId);

                redisTemplate.opsForValue().set(roomName, gameRoom); // 업데이트
                removeGameRoomIfEmpty(roomName); // 방이 비었으면 삭제
            }
        }
    }

    public void removeGameRoomIfEmpty(String roomName) {
        GameRoom gameRoom = (GameRoom) redisTemplate.opsForValue().get(roomName);
        if (gameRoom != null && gameRoom.getCurrentPopulation() == 0) {
            redisTemplate.delete(roomName); // 방 삭제
        }
    }

    public void endGame(String roomName, Map<Long, Integer> scores) {
        GameRoom gameRoom = (GameRoom) redisTemplate.opsForValue().get(roomName);
        if (gameRoom != null && "게임중".equals(gameRoom.getGameRoomStatus())) {
            gameRoom.setGameRoomStatus("대기중");
            redisTemplate.opsForValue().set(roomName, gameRoom); // 상태 업데이트

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
        return (GameRoom) redisTemplate.opsForValue().get(gameRoomName);
    }


    public long generateGameRoomId() {
        return redisTemplate.opsForValue().increment("gameRoomIdCounter"); // "gameRoomIdCounter" 키를 사용하여 자동 증가
    }
}


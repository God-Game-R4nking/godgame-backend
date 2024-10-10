package com.example.godgame.gameroom.service;

import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.chat.service.ChatService;
import com.example.godgame.game.entity.Game;
import com.example.godgame.game.repository.GameRepository;
import com.example.godgame.game.service.GameService;
import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.gameroom.dto.GameRoomResponseDto;
import com.example.godgame.history.entity.GameHistory;
import com.example.godgame.history.entity.GameRoomHistory;
import com.example.godgame.history.repository.GameHistoryRepository;
import com.example.godgame.history.repository.GameRoomHistoryRepository;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.prefs.BackingStoreException;

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

    @Autowired
    private Map<String, GameService> gameServiceMap = new HashMap<>();

    @Autowired
    private GameRepository gameRepository;
    private Set<String> validGameNames;
  
    @Autowired
    private MemberService memberService;

    @Autowired
    public GameRoomService(List<GameService> gameServices) {
        // 각 게임 서비스들을 매핑
        for (GameService gameService : gameServices) {
            gameServiceMap.put(gameService.getClass().getSimpleName(), gameService);
        }
    }

    public GameService getGameService(String gameName) {
        return gameServiceMap.get(gameName);
    }

    public GameRoomResponseDto createGameRoom(GameRoom gameRoom) {
        // 게임 이름 유효성 검사
        if (!isValidGameName(gameRoom.getGameName())) {
            throw new BusinessLogicException(ExceptionCode.GAME_NOT_FOUND);
        }

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

        // (추가) 게임 선택 및 서비스 호출
        GameService gameService = gameServiceMap.get(gameRoom.getGameName());
        if(gameService == null) {
            throw new BusinessLogicException(ExceptionCode.GAME_NOT_FOUND);
        } else {
            gameService.initializeGameRoom(gameRoom);
        }

        // 게임룸 히스토리 생성 및 DB 저장
        GameRoomHistory gameRoomHistory = new GameRoomHistory();
        gameRoomHistory.setCurrentPopulation(gameRoom.getCurrentPopulation());
        gameRoomHistory.setCreatedAt(LocalDateTime.now());
        gameRoomHistory.setModifiedAt(LocalDateTime.now());
        gameRoomHistoryRepository.save(gameRoomHistory);

        System.out.println("Game room created with ID: " + gameRoomId);
        Member findMember = memberService.findVerifiedMemberId(gameRoom.getMemberIds().get(0));
        // GameRoomResponseDto 반환
        return new GameRoomResponseDto(gameRoomId, gameRoom.getGameName(),
                gameRoom.getGameRoomName(),
                gameRoom.getCurrentPopulation(),
                gameRoom.getGameRoomStatus(),
                gameRoom.getMaxPopulation(),
                gameRoom.getMemberIds(),
                findMember.getNickName());
    }

    public void startGame(long gameRoomId) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get("gameRoom:" + gameRoomId);
        GameRoom gameRoom = convertFromJson(gameRoomJson); // JSON에서 역직렬화
        if (gameRoom != null && "대기중".equals(gameRoom.getGameRoomStatus())) {
            gameRoom.setGameRoomStatus("게임중");

            redisGameRoomTemplate.opsForValue().set("gameRoom:" + gameRoomId, convertToFormattedJson(gameRoom)); // JSON으로 업데이트

            GameRoomHistory gameRoomHistory = new GameRoomHistory();
            gameRoomHistory.setCurrentPopulation(gameRoom.getCurrentPopulation());
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

    public GameRoomResponseDto joinGame(long gameRoomId, Long memberId) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get("gameRoom:" + gameRoomId);
        GameRoom gameRoom = convertFromJson(gameRoomJson); // JSON에서 역직렬화

        // 게임 방이 존재하지 않거나 게임이 시작된 경우
        if (gameRoom == null || "게임중".equals(gameRoom.getGameRoomStatus())) {
            throw new BusinessLogicException(ExceptionCode.GAME_ROOM_JOIN_ERROR);
        }

        // 최대 인원수 초과시 입장 실패
        if (gameRoom.getCurrentPopulation() >= gameRoom.getMaxPopulation()) {
            throw new BusinessLogicException(ExceptionCode.GAME_ROOM_JOIN_ERROR);
        }

        // 이미 존재하는 멤버 아이디일 경우 입장 실패
        if (gameRoom.getMemberIds().contains(memberId)) {
            throw new BusinessLogicException(ExceptionCode.GAME_ROOM_JOIN_ERROR);
        }

        // 멤버를 추가하고 현재 인원 수를 증가시킴
        gameRoom.getMemberIds().add(memberId);
        gameRoom.setCurrentPopulation(gameRoom.getCurrentPopulation() + 1); // 인원수 증가

        // 게임룸을 다시 Redis에 저장
        String updatedJsonGameRoom = convertToFormattedJson(gameRoom);
        redisGameRoomTemplate.opsForValue().set("gameRoom:" + gameRoomId, updatedJsonGameRoom);

        Member findMember = memberService.findVerifiedMemberId(gameRoom.getMemberIds().get(0));
        return new GameRoomResponseDto(gameRoomId,
                gameRoom.getGameName(),
                gameRoom.getGameRoomName(),
                gameRoom.getCurrentPopulation(),
                gameRoom.getGameRoomStatus(),
                gameRoom.getMaxPopulation(),
                gameRoom.getMemberIds(),
                findMember.getNickName());
    }



    public GameRoomResponseDto leaveGame(long gameRoomId, Long memberId) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get("gameRoom:" + gameRoomId);
        GameRoom gameRoom = convertFromJson(gameRoomJson);

        // 게임 방이 존재하지 않거나 멤버가 존재하지 않는 경우
        if (gameRoom == null || !gameRoom.getMemberIds().contains(memberId)) {
            throw new BusinessLogicException(ExceptionCode.GAME_ROOM_JOIN_ERROR); // 게임 방이 없거나 해당 멤버가 없음
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

            // 게임 종료 시 Redis에 저장된 모든 채팅 메시지를 MongoDB에 저장
            chatService.saveAllChatsFromRedis(gameRoomId);
            Member findMember = memberService.findVerifiedMemberId(gameRoom.getMemberIds().get(0));

            return new GameRoomResponseDto(gameRoomId,
                    gameRoom.getGameName(),
                    gameRoom.getGameRoomName(),
                    gameRoom.getCurrentPopulation(),
                    gameRoom.getGameRoomStatus(),
                    gameRoom.getMaxPopulation(),
                    gameRoom.getMemberIds(),
                    findMember.getNickName());
        }

        throw new BusinessLogicException(ExceptionCode.LEAVE_FAIL);
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
            if (gameRoom.getMemberIds() != null) {
                for (Long memberId : gameRoom.getMemberIds()) {
                    GameHistory gameHistory = new GameHistory();
                    gameHistory.setMemberId(memberId);
                    gameHistory.setGameName(gameRoom.getGameName());
                    gameHistory.setScore(scores.getOrDefault(memberId, 0)); // 점수 가져오기, 없으면 0
                    // createdAt, modifiedAt은 기본값으로 설정되므로 필요 없음
                    gameHistoryRepository.save(gameHistory);
                }
            }
        }

        // 게임 종료 시 Redis에 저장된 모든 채팅 메시지를 MongoDB에 저장
        chatService.saveAllChatsFromRedis(gameRoom.getGameRoomId());
    }

    public GameRoom getGameRoom(String gameRoomName) {
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get(gameRoomName);
        return convertFromJson(gameRoomJson); // JSON에서 역직렬화
    }

    public GameRoomResponseDto getGameRoom(Long gameRoomId) {
        // Redis에서 해당 gameRoomId로 저장된 게임룸의 JSON 데이터를 가져옴
        String gameRoomJson = redisGameRoomTemplate.opsForValue().get("gameRoom:" + gameRoomId);

        // 가져온 JSON 데이터를 GameRoom 객체로 역직렬화
        GameRoom gameRoom = convertFromJson(gameRoomJson);

        if (gameRoom == null) {
            return null; // 게임룸이 존재하지 않으면 null 반환 또는 예외 처리
        }

        // 첫 번째 멤버의 닉네임 조회
        Member findMember = memberService.findVerifiedMemberId(gameRoom.getMemberIds().get(0));

        // GameRoomResponseDto 객체 생성
        GameRoomResponseDto gameRoomResponseDto = new GameRoomResponseDto(
                gameRoom.getGameRoomId(),
                gameRoom.getGameName(),
                gameRoom.getGameRoomName(),
                gameRoom.getCurrentPopulation(),
                gameRoom.getGameRoomStatus(),
                gameRoom.getMaxPopulation(),
                gameRoom.getMemberIds(),
                findMember.getNickName()
        );

        return gameRoomResponseDto;
    }

    public List<GameRoomResponseDto> getGameRooms() {
        // Redis에서 모든 게임룸의 키를 가져옵니다.
        Set<String> gameRoomKeys = redisGameRoomTemplate.keys("gameRoom:*");

        if (gameRoomKeys == null || gameRoomKeys.isEmpty()) {
            return Collections.emptyList(); // 게임룸이 없는 경우 빈 리스트 반환
        }

        List<GameRoom> gameRooms = new ArrayList<>();

        // 각 게임룸 키에 대해 JSON을 가져오고, 역직렬화하여 리스트에 추가
        for (String key : gameRoomKeys) {
            String gameRoomJson = redisGameRoomTemplate.opsForValue().get(key);
            GameRoom gameRoom = convertFromJson(gameRoomJson);
            if (gameRoom != null) {
                gameRooms.add(gameRoom);
            }
        }


        List<GameRoomResponseDto> list = new ArrayList<>();
        for(GameRoom gameRoom : gameRooms){
            Member findMember = memberService.findVerifiedMemberId(gameRoom.getMemberIds().get(0));
            GameRoomResponseDto gameRoomResponseDto = new GameRoomResponseDto(gameRoom.getGameRoomId(),
                    gameRoom.getGameName(),
                    gameRoom.getGameRoomName(),
                    gameRoom.getCurrentPopulation(),
                    gameRoom.getGameRoomStatus(),
                    gameRoom.getMaxPopulation(),
                    gameRoom.getMemberIds(),
                    findMember.getNickName());
            list.add(gameRoomResponseDto);
        }

        return list;
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

    public void publishToGameRoom(Long gameRoomId, String message) {
        String topic = "gameRoom:" + gameRoomId;
        redisGameRoomTemplate.convertAndSend(topic, message); // Redis를 통해 메시지 퍼블리시
    }

    private boolean isValidGameName(String gameName) {
        List<Game> games = gameRepository.findAll();
        return games.stream().anyMatch(game -> game.getGameName().equals(gameName));
    }

}

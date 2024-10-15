package com.example.godgame.catchmind.service;

import com.example.godgame.catchmind.entity.Catchmind;
import com.example.godgame.catchmind.repository.CatchmindRepository;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.game.service.CatchmindGameService;
import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.service.MemberService;
import com.example.godgame.websocket.webchat.ChattingMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.godgame.member.entity.Member.MemberGameStatus.MEMBER_PLAY;
import static com.example.godgame.member.entity.Member.MemberGameStatus.MEMBER_WAIT;

@Service("Catchmind")
@Transactional
@EnableScheduling
public class CatchmindService extends CatchmindGameService {

    private final CatchmindRepository catchmindRepository;
    private final RedisTemplate<String, GameRoom> redisStringGameRoomTemplate;
    private final RedisTemplate<String, String> redisGameRoomTemplate;
    private final RedisTemplate<String, ChattingMessage> redisChattingMessageTemplate;
    private final RedisTemplate<String, List<Catchmind>> redisCatchmindTemplate;
    private final RedisTemplate<String, Object> redisHashGameRoomTemplate;
    private final MemberService memberService;

    // GameRoom별 상태를 관리하는 맵
    private final Map<Long, List<Long>> gameRoomMemberIds = new HashMap<>();
    private final Map<Long, String> currentDrawers = new HashMap<>();
    private final Map<Long, String> currentAnswers = new HashMap<>();
    private final Map<Long, Integer> currentQuestionIndexes = new HashMap<>();
    private final Map<Long, Map<Long, Integer>> gameRoomScores = new HashMap<>();
    private final Map<Long, Integer> gameRoomRoundTimes = new HashMap<>();
    private final Map<Long, ScheduledExecutorService> schedulers = new HashMap<>();
    private boolean isGameRunning;
    private final Map<Long, Boolean> isTimerRunning = new HashMap<>();

    public CatchmindService(CatchmindRepository catchmindRepository, RedisTemplate<String, GameRoom> redisTemplate, RedisTemplate<String, String> redisGameRoomTemplate, RedisTemplate<String, ChattingMessage> redisChattingMessageTemplate, RedisTemplate<String, List<Catchmind>> redisCatmindTemplate, RedisTemplate<String, Object> redisHashGameRoomTemplate, MemberService memberService) {
        this.catchmindRepository = catchmindRepository;
        this.redisStringGameRoomTemplate = redisTemplate;
        this.redisGameRoomTemplate = redisGameRoomTemplate;
        this.memberService = memberService;
        this.redisChattingMessageTemplate = redisChattingMessageTemplate;
        this.redisCatchmindTemplate = redisCatmindTemplate;
        this.redisHashGameRoomTemplate = redisHashGameRoomTemplate;
    }

    @Override
    public void initializeGameRoom(GameRoom gameRoom) {

        gameRoom.setGameRoomStatus("playing");

        gameRoomMemberIds.put(gameRoom.getGameRoomId(), gameRoom.getMemberIds()); // 멤버 리스트를 맵에 추가
        currentDrawers.put(gameRoom.getGameRoomId(), null);
        currentAnswers.put(gameRoom.getGameRoomId(), null);
        currentQuestionIndexes.put(gameRoom.getGameRoomId(), 0);


        for(Long memberId : gameRoom.getMemberIds()){
            memberService.findVerifiedMemberId(memberId);
            gameRoom.setScores(new HashMap<>());
            gameRoom.getScores().put(memberId, 0);
        }
        gameRoomScores.put(gameRoom.getGameRoomId(), gameRoom.getScores());
        isGameRunning = false;
        isTimerRunning.put(gameRoom.getGameRoomId(), false);
    }

    @Override
    public void startCatchmind(GameRoom gameRoom, int count) {
        System.out.println("Starting Catchmind for Game Room ID: " + gameRoom.getGameRoomId());

        try {
            initializeGameRoom(gameRoom);
            System.out.println("Game room initialized.");

            List<Long> memberIds = gameRoomMemberIds.get(gameRoom.getGameRoomId());
            if (memberIds == null || memberIds.size() < 2) {
                System.out.println("Not enough members.");
                throw new BusinessLogicException(ExceptionCode.NEED_MORE_MEMBER);
            }

            for (Long memberId : memberIds) {
                Member member = memberService.findVerifiedMemberId(memberId);
                member.setMemberGameStatus(MEMBER_PLAY);
            }

            isGameRunning = true;
            currentDrawers.put(gameRoom.getGameRoomId(), findNickname(memberIds.get(0)));

            List<Catchmind> questions = findRandomWord(count);
            if (questions.isEmpty()) {
                System.out.println("No questions available.");
                throw new BusinessLogicException(ExceptionCode.QUESTION_NOT_AVAILABLE);
            }

            redisCatchmindTemplate.opsForValue().set("chatchMind:" + gameRoom.getGameRoomId(), questions);
            currentAnswers.put(gameRoom.getGameRoomId(), questions.get(0).getWord());

            saveGameRoomState(gameRoom);
            System.out.println("Catchmind started successfully.");
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void startRound(GameRoom gameRoom) {

        ChattingMessage chattingMessage = new ChattingMessage();
        chattingMessage.setContent(currentDrawers.get(gameRoom.getGameRoomId()));
        chattingMessage.setType("CURRENT_DRAWER");
        chattingMessage.setMemberId(memberService.findVerifiedNickName(currentDrawers.get(gameRoom.getGameRoomId())).getMemberId());
        chattingMessage.setNickName(currentDrawers.get(gameRoom.getGameRoomId()));
        chattingMessage.setGameRoomId(gameRoom.getGameRoomId());
        chattingMessage.setCreatedAt(null);
        String jsonString = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonString = objectMapper.writeValueAsString(chattingMessage);

            System.out.println("DRAWER jsonString : " + jsonString);
            redisChattingMessageTemplate.convertAndSend("gameRoom:" + gameRoom.getGameRoomId(), jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        chattingMessage.setContent(currentAnswers.get(gameRoom.getGameRoomId()));
        chattingMessage.setType("CURRENT_ANSWER");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonString = objectMapper.writeValueAsString(chattingMessage);

            System.out.println("ANSWER jsonString : " + jsonString); // 결과 확인
            redisChattingMessageTemplate.convertAndSend("gameRoom:" + gameRoom.getGameRoomId(), jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTimer(GameRoom gameRoom) {

        if(redisHashGameRoomTemplate.opsForHash().get("hashKey:" + gameRoom.getGameRoomId(),
                "isTimerRunning:").equals(false)) {

            isTimerRunning.put(gameRoom.getGameRoomId(), true);
            gameRoomRoundTimes.put(gameRoom.getGameRoomId(), 60);
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            schedulers.put(gameRoom.getGameRoomId(), scheduler);

            scheduler.scheduleAtFixedRate(() -> {
                int time = gameRoomRoundTimes.get(gameRoom.getGameRoomId());
                if (time > 0) {
                    time--;
                    gameRoomRoundTimes.put(gameRoom.getGameRoomId(), time);
                    System.out.println("Remaining time: " + time);
                } else {
                    isTimerRunning.put(gameRoom.getGameRoomId(), false);
                    scheduler.shutdown();
                }
            }, 0, 1, TimeUnit.SECONDS);

            ChattingMessage chattingMessage = new ChattingMessage();
            chattingMessage.setContent(null);
            chattingMessage.setType("STOP_TIMER");
            chattingMessage.setMemberId(0);
            chattingMessage.setNickName(null);
            chattingMessage.setGameRoomId(gameRoom.getGameRoomId());
            chattingMessage.setCreatedAt(null);
            String jsonString = "";
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                jsonString = objectMapper.writeValueAsString(chattingMessage);

                System.out.println("STOP_TIMER jsonString : " + jsonString);
                redisChattingMessageTemplate.convertAndSend("gameRoom:" + gameRoom.getGameRoomId(), jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void stopTimer(GameRoom gameRoom) {

        if (redisHashGameRoomTemplate.opsForHash().get("hashKey:" + gameRoom.getGameRoomId(),
                "isTimerRunning:").equals(true)) {
            ScheduledExecutorService scheduler = schedulers.get(gameRoom.getGameRoomId());
            if (scheduler != null) {
                scheduler.shutdownNow();
                schedulers.remove(gameRoom.getGameRoomId());
            }
            isTimerRunning.put(gameRoom.getGameRoomId(), false);
        }
    }

    @Override
    public void guessAnswer(GameRoom gameRoom, Member member, ChattingMessage parseChattingMessage) {

        if(parseChattingMessage.getType().equals("CORRECT_ANSWER") && parseChattingMessage.getContent().equals(getCurrentAnswer(gameRoom))) {
            Map<Long, Integer> scores = gameRoom.getScores();
            scores.put(member.getMemberId(), scores.get(member.getMemberId()) + 1);
            redisHashGameRoomTemplate.opsForHash().put("hashKey:" + gameRoom.getGameRoomId(),
                    "gameRoomScores:", scores);
        }
    }


    public void endRound(GameRoom gameRoom) {
        schedulers.get(gameRoom.getGameRoomId()).schedule(() -> {

            ChattingMessage chattingMessage = new ChattingMessage();
            chattingMessage.setContent(currentAnswers.get(gameRoom.getGameRoomId()));
            chattingMessage.setType("END_ROUND");
            chattingMessage.setMemberId(memberService.findVerifiedNickName(currentDrawers.get(gameRoom.getGameRoomId())).getMemberId());
            chattingMessage.setNickName(currentDrawers.get(gameRoom.getGameRoomId()));
            chattingMessage.setGameRoomId(gameRoom.getGameRoomId());
            chattingMessage.setCreatedAt(null);
            String jsonString = "";
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                jsonString = objectMapper.writeValueAsString(chattingMessage);

                System.out.println("END_ROUND jsonString : " + jsonString);
                redisChattingMessageTemplate.convertAndSend("gameRoom:" + gameRoom.getGameRoomId(), jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int currentIndex = currentQuestionIndexes.get(gameRoom.getGameRoomId()) + 1;
            currentQuestionIndexes.put(gameRoom.getGameRoomId(), currentIndex);

            List<Catchmind> questions = findRandomWord(currentIndex);

            if (currentIndex < questions.size()) {
                currentAnswers.put(gameRoom.getGameRoomId(), questions.get(currentIndex).getWord());
                List<Long> memberIds = gameRoomMemberIds.get(gameRoom.getGameRoomId());
                currentDrawers.put(gameRoom.getGameRoomId(), findNickname(memberIds.get(currentIndex % memberIds.size())));
                saveGameRoomState(gameRoom);

            } else {

                chattingMessage = new ChattingMessage();
                chattingMessage.setContent(null);
                chattingMessage.setType("ALL_ROUND_END");
                chattingMessage.setMemberId(0);
                chattingMessage.setNickName(null);
                chattingMessage.setGameRoomId(gameRoom.getGameRoomId());
                chattingMessage.setCreatedAt(null);
                jsonString = "";
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    jsonString = objectMapper.writeValueAsString(chattingMessage);

                    System.out.println("ALL ROUND END jsonString : " + jsonString);
                    redisChattingMessageTemplate.convertAndSend("gameRoom:" + gameRoom.getGameRoomId(), jsonString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5, TimeUnit.SECONDS);
    }

    @Override
    public void endGame(GameRoom gameRoom, Map<Long, Integer> scores) {
        isGameRunning = false;
        if (schedulers.containsKey(gameRoom.getGameRoomId()) && schedulers.get(gameRoom.getGameRoomId()) != null) {
            schedulers.get(gameRoom.getGameRoomId()).shutdown();
        }

        gameRoom.setGameRoomStatus("wait");
        gameRoomScores.put(gameRoom.getGameRoomId(), scores);

        List<Long> memberIds = gameRoomMemberIds.get(gameRoom.getGameRoomId());
        for (Long memberId : memberIds) {
            Member member = memberService.findVerifiedMemberId(memberId);
            member.setMemberGameStatus(MEMBER_WAIT);
        }

        String updatedJsonGameRoom = convertToFormattedJson(gameRoom);
        redisStringGameRoomTemplate.opsForValue().set("gameRoom:" + updatedJsonGameRoom, gameRoom);


//        // redis에서 삭제
//        String roomId = "gameRoom" + gameRoom.getGameRoomId();
//        redisHashGameRoomTemplate.opsForHash().delete("currentDrawers", roomId);
//        redisHashGameRoomTemplate.opsForHash().delete("currentAnswers", roomId);
//        redisHashGameRoomTemplate.opsForHash().delete("currentQuestionIndexes", roomId);
//        redisHashGameRoomTemplate.opsForHash().delete("gameRoomScores", roomId);
//        redisHashGameRoomTemplate.opsForHash().delete("isGameRunning", roomId);
//
//        // 게임방 상태 초기화
//        gameRoomMembers.remove(gameRoom);
//        currentDrawers.remove(gameRoom);
//        currentAnswers.remove(gameRoom);
//        currentQuestionIndexes.remove(gameRoom);
//        gameRoomScores.remove(gameRoom);
//        schedulers.remove(gameRoom);
//        isTimerRunning.remove(gameRoom);

    }

    @Override
    public String getCurrentAnswer(GameRoom gameRoom) {
        return currentAnswers.get(gameRoom.getGameRoomId());
    }

    public List<Catchmind> findRandomWord(int count) {
        return catchmindRepository.findRandomCatchminds(count);
    }

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

    private ChattingMessage convertChattingMessageFromJson(String json) {
        if (json == null) {
            throw new IllegalArgumentException("Input JSON string cannot be null");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, ChattingMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting JSON to GameRoom", e);
        }
    }

    private void saveGameRoomState(GameRoom gameRoom) {
        String gameRoomKey = "hashKey:" + gameRoom.getGameRoomId();

        redisHashGameRoomTemplate.opsForHash().put(gameRoomKey, "currentDrawers", currentDrawers.get(gameRoom.getGameRoomId()));
        redisHashGameRoomTemplate.opsForHash().put(gameRoomKey, "currentAnswers", currentAnswers.get(gameRoom.getGameRoomId()));
        redisHashGameRoomTemplate.opsForHash().put(gameRoomKey, "currentQuestionIndexes:", currentQuestionIndexes.get(gameRoom.getGameRoomId()));
        redisHashGameRoomTemplate.opsForHash().put(gameRoomKey, "gameRoomScores", gameRoomScores.get(gameRoom.getGameRoomId()));
        redisHashGameRoomTemplate.opsForHash().put(gameRoomKey, "isGameRunning:", isGameRunning);
        redisHashGameRoomTemplate.opsForHash().put(gameRoomKey, "isTimerRunning:", isTimerRunning.get(gameRoom.getGameRoomId()));

        String updatedJsonGameRoom = convertToFormattedJson(gameRoom);
        redisGameRoomTemplate.opsForValue().set("gameRoom:" + gameRoom.getGameRoomId(), updatedJsonGameRoom);
    }

    private String findNickname(Long memberId) {
        Member member = memberService.findVerifiedMemberId(memberId);
        return member.getNickName();
    }

}
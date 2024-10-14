package com.example.godgame.catchmind.service;

import com.example.godgame.catchmind.entity.Catchmind;
import com.example.godgame.catchmind.repository.CatchmindRepository;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.game.service.CatchmindGameService;
import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.repository.MemberRepository;
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
    private final RedisTemplate<String, GameRoom> redisTemplate;
    private final RedisTemplate<String, ChattingMessage> redisChattindMessageTemplate;
    private final MemberRepository memberRepository;

    // GameRoom별 상태를 관리하는 맵
    private final Map<GameRoom, List<Member>> gameRoomMembers = new HashMap<>();
    private final Map<GameRoom, Member> currentDrawers = new HashMap<>();
    private final Map<GameRoom, String> currentAnswers = new HashMap<>();
    private final Map<GameRoom, Integer> currentQuestionIndexes = new HashMap<>();
    private final Map<GameRoom, Map<Member, Integer>> gameRoomScores = new HashMap<>();
    private final Map<GameRoom, Integer> gameRoomRoundTimes = new HashMap<>();
    private final Map<GameRoom, ScheduledExecutorService> schedulers = new HashMap<>();
    private boolean isGameRunning;
    private Map<GameRoom, Boolean> isTimerRunning = new HashMap<>();

    public CatchmindService(CatchmindRepository catchmindRepository, RedisTemplate<String, GameRoom> redisTemplate, MemberRepository memberRepository, RedisTemplate<String, ChattingMessage> redisChattindMessageTemplate) {
        this.catchmindRepository = catchmindRepository;
        this.redisTemplate = redisTemplate;
        this.memberRepository = memberRepository;
        this.redisChattindMessageTemplate = redisChattindMessageTemplate;
    }

    @Override
    public void initializeGameRoom(GameRoom gameRoom) {

        gameRoom.setGameRoomStatus("playing");

        gameRoomMembers.put(gameRoom, null);

        String gameRoomKey = "gameRoom:" + gameRoom.getGameRoomId();
        redisTemplate.opsForValue().set(gameRoomKey, gameRoom);
        GameRoom existingGameRoom = redisTemplate.opsForValue().get(gameRoomKey);

        if (existingGameRoom != null) {
            List<Long> memberIds = existingGameRoom.getMemberIds(); // GameRoom 객체에 멤버 리스트가 있어야 합니다.
            gameRoomMembers.put(gameRoom, memberRepository.findAllById(memberIds)); // 멤버 리스트를 맵에 추가
        } else {
            throw new BusinessLogicException(ExceptionCode.GAME_ROOM_NOT_FOUND);
        }

        currentDrawers.put(gameRoom, null);
        currentAnswers.put(gameRoom, null);
        currentQuestionIndexes.put(gameRoom, 0);
        gameRoomScores.put(gameRoom, new HashMap<>());
        gameRoomRoundTimes.put(gameRoom, 60);
        isGameRunning = false;

        redisTemplate.opsForValue().set("gameRoom:" + gameRoom.getGameRoomId(), gameRoom);
    }

    @Override
    public void startCatchmind(GameRoom gameRoom, int count) {

        initializeGameRoom(gameRoom);

        List<Member> members = gameRoomMembers.get(gameRoom);
        for (int i = 0; i < members.size(); i++) {
            members.get(i).setMemberGameStatus(MEMBER_PLAY);
        }

        if (members.size() < 2) {
            throw new BusinessLogicException(ExceptionCode.NEED_MORE_MEMBER);
        }

        isGameRunning = true;
        currentDrawers.put(gameRoom, members.get(0)); // 첫 번째 그릴 사람 설정

        List<Catchmind> questions = findRandomWord(count);
        if (questions.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_NOT_AVAILABLE);
        }

        Map<Member, Integer> scores = gameRoomScores.get(gameRoom);
        currentAnswers.put(gameRoom, questions.get(0).getWord());

        for (Member member : members) {
            gameRoomScores.get(gameRoom).put(member, 0); // 초기 점수 설정
        }
        startRound(gameRoom, scores);
    }

    public void startRound(GameRoom gameRoom, Map<Member, Integer> scores) {

        ChattingMessage chattingMessage = new ChattingMessage();
        chattingMessage.setContent(currentDrawers.get(gameRoom).getNickName());
        chattingMessage.setType("CURRENT_DRAWER");
        chattingMessage.setMemberId(currentDrawers.get(gameRoom).getMemberId());
        chattingMessage.setNickName(currentDrawers.get(gameRoom).getNickName());
        chattingMessage.setGameRoomId(gameRoom.getGameRoomId());
        chattingMessage.setCreatedAt(null);
        String jsonString = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonString = objectMapper.writeValueAsString(chattingMessage);

            System.out.println("DRAWER jsonString : " + jsonString);
            redisChattindMessageTemplate.convertAndSend("gameRoom:" + gameRoom.getGameRoomId(), jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        chattingMessage.setContent(currentAnswers.get(gameRoom));
        chattingMessage.setType("CURRENT_ANSWER");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonString = objectMapper.writeValueAsString(chattingMessage);

            System.out.println("ANSWER jsonString : " + jsonString); // 결과 확인
            redisChattindMessageTemplate.convertAndSend("gameRoom:" + gameRoom.getGameRoomId(), jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        startTimer(gameRoom, scores);

    }

    public void startTimer(GameRoom gameRoom, Map<Member, Integer> scores) {

        if (isTimerRunning.getOrDefault(gameRoom, false)) {
            return;
        }

        isTimerRunning.put(gameRoom, true);

        gameRoomRoundTimes.put(gameRoom, 60);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        schedulers.put(gameRoom, scheduler);

        scheduler.scheduleAtFixedRate(() -> {
            int time = gameRoomRoundTimes.get(gameRoom);
            if (time > 0) {
                time--;
                gameRoomRoundTimes.put(gameRoom, time);
                System.out.println("Remaining time: " + time);
            } else {
                isTimerRunning.put(gameRoom, false);
                endRound(gameRoom, scores);
                scheduler.shutdown();
            }
        },0, 1, TimeUnit.SECONDS);
    }

    public void stopTimer(GameRoom gameRoom) {

        if (isTimerRunning.getOrDefault(gameRoom, false)) {
            ScheduledExecutorService scheduler = schedulers.get(gameRoom);
            if (scheduler != null) {
                scheduler.shutdownNow();
            }
            isTimerRunning.put(gameRoom, false);
        }
    }

    @Override
    public boolean guessAnswer(GameRoom gameRoom, Member member, ChattingMessage parseChattingMessage) {

        if(parseChattingMessage.getType().equals("CORRECT_ANSWER") && parseChattingMessage.getContent().equals(getCurrentAnswer(gameRoom))) {
            Map<Member, Integer> scores = gameRoomScores.get(gameRoom);
            scores.put(member, scores.get(member) + 1);

            return true;
        }
        return false;
    }

    public void endRound(GameRoom gameRoom, Map<Member, Integer> scores) {
        schedulers.get(gameRoom).schedule(() -> {
            int currentIndex = currentQuestionIndexes.get(gameRoom) + 1;
            currentQuestionIndexes.put(gameRoom, currentIndex);

            List<Catchmind> questions = findRandomWord(currentIndex);

            if (currentIndex < questions.size()) {
                currentAnswers.put(gameRoom, questions.get(currentIndex).getWord());
                List<Member> members = gameRoomMembers.get(gameRoom);
                currentDrawers.put(gameRoom, members.get(currentIndex % members.size()));
            } else {
                if(!endGame(gameRoom, scores)) {
                    throw new BusinessLogicException(ExceptionCode.GAME_END_ERROR);
                }
            }
        }, 5, TimeUnit.SECONDS);
        startRound(gameRoom, scores);
    }

    @Override
    public boolean endGame(GameRoom gameRoom, Map<Member, Integer> scores) {
        isGameRunning = false;
        if (schedulers.containsKey(gameRoom) && schedulers.get(gameRoom) != null) {
            schedulers.get(gameRoom).shutdown();
        }

        gameRoom.setGameRoomStatus("wait");
        gameRoomScores.put(gameRoom, scores);



        List<Member> members = gameRoomMembers.get(gameRoom);
        for (int i = 0; i < members.size(); i++) {
            members.get(i).setMemberGameStatus(MEMBER_WAIT);
        }

        String updatedJsonGameRoom = convertToFormattedJson(gameRoom);
        redisTemplate.opsForValue().set("gameRoom:"+ updatedJsonGameRoom, gameRoom);

        return true;
    }

    public Map<Member, Integer> getScores (GameRoom gameRoom) {
        return gameRoomScores.get(gameRoom);
    }

    @Override
    public String getCurrentAnswer(GameRoom gameRoom) {
        return currentAnswers.get(gameRoom);
    }

    public List<Catchmind> findRandomWord(int count) {
        return catchmindRepository.findRandomCatchminds(count);
    }

    public List<Member> getMembersFromIds(List<Long> memberIds) {

        if (memberIds == null || memberIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<Member> members = memberRepository.findAllById(memberIds);
        if (members.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        return members;
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
}
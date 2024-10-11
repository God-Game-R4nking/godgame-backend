package com.example.godgame.catchmind.service;

import com.example.godgame.catchmind.entity.Catchmind;
import com.example.godgame.catchmind.repository.CatchmindRepository;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.game.service.CatchmindGameService;
import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.history.repository.GameHistoryRepository;
import com.example.godgame.history.service.GameHistoryService;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.repository.MemberRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service("Catchmind")
@Transactional
@EnableScheduling
public class CatchmindService extends CatchmindGameService {


    private final CatchmindRepository catchmindRepository;
    private final RedisTemplate<String, GameRoom> redisTemplate;
    private final MemberRepository memberRepository;
    private final GameHistoryService gameHistoryService;
    private final GameHistoryRepository gameHistoryRepository;

    // GameRoom별 상태를 관리하는 맵
    private final Map<GameRoom, List<Member>> gameRoomMembers = new HashMap<>();
    private final Map<GameRoom, Member> currentDrawers = new HashMap<>();
    private final Map<GameRoom, String> currentAnswers = new HashMap<>();
    private final Map<GameRoom, Integer> currentQuestionIndexes = new HashMap<>();
    private final Map<GameRoom, Map<Member, Integer>> gameRoomScores = new HashMap<>();
    private final Map<GameRoom, Integer> gameRoomRoundTimes = new HashMap<>();
    private final Map<GameRoom, ScheduledExecutorService> schedulers = new HashMap<>();
    private boolean isGameRunning;

    public CatchmindService(CatchmindRepository catchmindRepository, RedisTemplate<String, GameRoom> redisTemplate, MemberRepository memberRepository, GameHistoryService gameHistoryService, GameHistoryRepository gameHistoryRepository) {
        this.catchmindRepository = catchmindRepository;
        this.redisTemplate = redisTemplate;
        this.memberRepository = memberRepository;
        this.gameHistoryService = gameHistoryService;
        this.gameHistoryRepository = gameHistoryRepository;
    }

    @Override
    public void initializeGameRoom(GameRoom gameRoom) {
        gameRoom.setGameRoomStatus("ACTIVE");

        gameRoomMembers.put(gameRoom, new ArrayList<>());
        currentDrawers.put(gameRoom, null);
        currentAnswers.put(gameRoom, null);
        currentQuestionIndexes.put(gameRoom, 0);
        gameRoomScores.put(gameRoom, new HashMap<>());
        gameRoomRoundTimes.put(gameRoom, 60);
        isGameRunning = false;

        redisTemplate.opsForValue().set("gameroom:" + gameRoom.getGameRoomId(), gameRoom);
    }

    @Override
    public void startCatchmind(GameRoom gameRoom, int count) {
        List<Member> members = gameRoomMembers.get(gameRoom);

        if (members.size() < 3) {
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
        currentQuestionIndexes.put(gameRoom, 0);

        for (Member member : members) {
            gameRoomScores.get(gameRoom).put(member, 0); // 초기 점수 설정
        }
        startTimer(gameRoom, scores);
    }

    @Override
    public boolean endGame(GameRoom gameRoom, Map<Member, Integer> scores) {
        isGameRunning = false;
        if (schedulers.containsKey(gameRoom) && schedulers.get(gameRoom) != null) {
            schedulers.get(gameRoom).shutdown();

            gameRoomScores.put(gameRoom, scores);

            return true;
        }
        return false;
    }

    private void startTimer(GameRoom gameRoom, Map<Member, Integer> scores) {
        gameRoomRoundTimes.put(gameRoom, 60);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        schedulers.put(gameRoom, scheduler);

        scheduler.scheduleAtFixedRate(() -> {
            if (isGameRunning) {
                int time = gameRoomRoundTimes.get(gameRoom) - 1;
                gameRoomRoundTimes.put(gameRoom, time);

                if (time <= 0) {
                    endRound(gameRoom, scores);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void endRound(GameRoom gameRoom, Map<Member, Integer> scores) {
        schedulers.get(gameRoom).schedule(() -> {
            int currentIndex = currentQuestionIndexes.get(gameRoom) + 1;
            currentQuestionIndexes.put(gameRoom, currentIndex);

            List<Catchmind> questions = findRandomWord(currentIndex); // 질문 업데이트

            if (currentIndex < questions.size()) {
                currentAnswers.put(gameRoom, questions.get(currentIndex).getWord());
                gameRoomRoundTimes.put(gameRoom, 60);
                List<Member> members = gameRoomMembers.get(gameRoom);
                currentDrawers.put(gameRoom, members.get(currentIndex % members.size()));
            } else {
                if(!endGame(gameRoom, scores)) {
                    throw new BusinessLogicException(ExceptionCode.GAME_END_ERROR);
                }
            }
        }, 5, TimeUnit.SECONDS);
        startTimer(gameRoom, scores);
    }

    @Override
    public boolean guessAnswer(GameRoom gameRoom, Member member, String guess) {
        if (isGameRunning) {
            String currentAnswer = currentAnswers.get(gameRoom);
            if (currentAnswer != null && currentAnswer.equalsIgnoreCase(guess)) {
                Map<Member, Integer> scores = gameRoomScores.get(gameRoom);
                scores.put(member, scores.get(member) + 1); // 점수 추가
                endRound(gameRoom, scores);
                return true;
            }
        }
        return false;
    }

    public GameRoom getGameRoomById(Long gameRoomId) {
        GameRoom gameRoom = redisTemplate.opsForValue().get("gameroom:" + gameRoomId);
        if (gameRoom == null) {
            throw new BusinessLogicException(ExceptionCode.GAME_ROOM_NOT_FOUND);
        }
        return gameRoom;
    }

    @Override
    public boolean guessAnswer(Member member, String guess) {
        return false;
    }

    @Override
    public String getCurrentAnswer() {
        return "";
    }

    @Override
    public String getCurrentAnswer(GameRoom gameRoom) {
        return currentAnswers.get(gameRoom);
    }

    public List<Catchmind> findRandomWord(int count) {
        return catchmindRepository.findRandomCatchminds(count);
    }

    public List<Member> getMembers(GameRoom gameRoom) {
        return gameRoomMembers.get(gameRoom);
    }

    public int getScoreForMember(GameRoom gameRoom, Member member) {
        return gameRoomScores.get(gameRoom).getOrDefault(member, 0);
    }

    public int getRoundTime(GameRoom gameRoom) {
        return gameRoomRoundTimes.get(gameRoom);
    }

    public Member getMemberById(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember.orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
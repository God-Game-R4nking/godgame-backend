package com.example.godgame.catchmind.service;

import com.example.godgame.Image.country.entity.CountryImage;
import com.example.godgame.Image.country.repository.CountryImageRepository;
import com.example.godgame.catchmind.entity.Catchmind;
import com.example.godgame.catchmind.repository.CatchmindRepository;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.game.service.GameService;
import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.member.entity.Member;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service("Catchmind")
@Transactional
@EnableScheduling
public class CatchmindService implements GameService {

    private List<Member> members = new ArrayList<>();
    private Member currentDrawer;
    private boolean isGameRunning = false;
    private String currentAnswer;
    private List<Catchmind> questions;
    private int currentQuestionIndex = 0;
    private int currentDrawerIndex = 0;
    private final CatchmindRepository catchmindRepository;

    private Map<Member, Integer> scores = new HashMap<>();
    private ScheduledExecutorService scheduler;
    private int roundTime = 60;

    public CatchmindService(CatchmindRepository catchmindRepository) {
        this.catchmindRepository = catchmindRepository;
    }

    @Override
    public void initializeGameRoom(GameRoom gameRoom) {

        gameRoom.setCurrentPopulation(0);
        gameRoom.setGameRoomStatus("ACTIVE");
        questions = new ArrayList<>();
        currentQuestionIndex = 0;
        currentDrawerIndex = 0;
        scores.clear();
    }

    public void startGame(GameRoom gameRoom, int count) {
        if(members.size() < 2) {
            throw new BusinessLogicException(ExceptionCode.NEED_MORE_MEMBER);
        }
        isGameRunning = true;
        currentDrawer = members.get(currentDrawerIndex);

        questions = findRandomWord(count);
        if(questions.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.QUESTION_NOT_AVAILABLE);
        }

        currentAnswer = questions.get(currentQuestionIndex).getWord();

        startTimer();

        for(Member member : members) {
            scores.put(member, 0);
        }
    }

    @Override
    public void endGame(GameRoom gameRoom) {
        isGameRunning = false;
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    private void startTimer() {

        roundTime = 60;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(()-> {
            if(isGameRunning) {
                roundTime--;
                if(roundTime <= 0) {
                    endRound();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void endRound() {
        scheduler.schedule(() -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                currentAnswer = questions.get(currentQuestionIndex).getWord();
                roundTime = 60;
                currentDrawerIndex++;
                if(currentDrawerIndex >= members.size()) {
                    currentDrawerIndex = 0;
                }
            } else {
                endGame(null); // 모든 라운드 종료
            }
        }, 5, TimeUnit.SECONDS); // 5초 후 호출
    }

    @Override
    public boolean guessAnswer(Member member, String guess) {

        if(isGameRunning) {
            if(currentAnswer != null && currentAnswer.equalsIgnoreCase(guess)) {
                scores.put(member, scores.get(member) + 1);
                endRound();
                return true;
            }
        }
        return false;
    }

    @Override
    public String getCurrentAnswer() {
        return currentAnswer;
    }

    public List<Catchmind> findRandomWord(int count){
        return catchmindRepository.findRandomCatchminds(count);
    }
}

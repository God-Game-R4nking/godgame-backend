package com.example.godgame.music.service;

import com.example.godgame.catchmind.entity.Catchmind;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.game.service.MusicGameService;
import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.member.entity.Member;
import com.example.godgame.music.dto.MusicDto;
import com.example.godgame.music.entity.Music;
import com.example.godgame.music.mapper.MusicMapper;
import com.example.godgame.music.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service("Music")
@Transactional
@EnableScheduling
public class MusicService extends MusicGameService {

    private final MusicRepository musicRepository;
    private final RedisTemplate<String, GameRoom> redisTemplate;
    private final MusicMapper musicMapper;

    private final Map<GameRoom, List<Music>> gameRoomMusics = new HashMap<>();
    private final Map<GameRoom, List<Member>> gameRoomMembers = new HashMap<>();
    private final Map<GameRoom, Integer> currentMusicIndex = new HashMap<>();
    private final Map<GameRoom, String> currentAnswers = new HashMap<>();
    private final Map<GameRoom, Integer> gameRoomCounts = new HashMap<>();
    private boolean isGameRunning;
    private String era;
    private final Map<String, Integer> scores = new HashMap<>();
    private final Map<GameRoom, ScheduledExecutorService> scheduler = new HashMap<>();
    private ScheduledFuture<?> timeoutTask;

    @Autowired
    public MusicService(MusicRepository musicRepository, RedisTemplate<String, GameRoom> redisTemplate, MusicMapper musicMapper) {
        this.musicRepository = musicRepository;
        this.redisTemplate = redisTemplate;
        this.musicMapper = musicMapper;
    }

    @Override
    public void initializeGameRoom(GameRoom gameRoom) {
        // 게임 방 초기화 로직
        currentMusicIndex.put(gameRoom, 0);
        gameRoomCounts.put(gameRoom, gameRoom.getCount());
        isGameRunning = false;
        scores.clear();
    }

    // 게임 시작
    public void startGame(GameRoom gameRoom, int count, String era) {
        List<Member> memberList = gameRoomMembers.get(gameRoom);
        if(memberList.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.NEED_MEMBERS);
        }

        gameRoomMembers.put(gameRoom, memberList);
        List<Music> gameRoomMusicList = musicRepository.findRandomMusics(count, era);
        if(gameRoomMusicList.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND);
        }

        gameRoomMusics.put(gameRoom, gameRoomMusicList);
        isGameRunning = true;
        scores.clear(); // 점수 초기화
    }

    // 현재 음악 정보 가져오기
    private MusicDto.Response getCurrentMusic(GameRoom gameRoom) {
        if (!isGameRunning || gameRoomMusics.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.GAME_NOT_PROCEED);
        }
        return musicMapper.musicToMusicResponseDto(getCurrentMusicList(gameRoom)
                .get(currentMusicIndex.get(gameRoom)));
    }

    private void endRound(GameRoom gameRoom, Map<Member, Integer> scores) {
        scheduler.get(gameRoom).schedule(() -> {
            int currentIndex = currentMusicIndex.get(gameRoom) + 1;
            currentMusicIndex.put(gameRoom, currentIndex);

            if (currentIndex < gameRoomCounts.get(gameRoom)) {
                currentAnswers.put(gameRoom, getCurrentMusic(gameRoom).getCurrentTitle());
            } else {
                endGame(gameRoom, scores); // 모든 라운드 종료
            }
        }, 5, TimeUnit.SECONDS);
    }

    // 정답 맞추기
//    public MusicDto.GuessResponse guessAnswer(String guess) {
//        if (currentMusicIndex == 0) {
//            return new MusicDto.GuessResponse(false, "게임이 진행 중이지 않습니다.");
//        }
//
//        Music currentMusic = gameRoomMusics.get().get(currentMusicIndex - 1);
//        boolean correct = currentMusic.getTitle().equalsIgnoreCase(guess) ||
//                currentMusic.getSinger().equalsIgnoreCase(guess);
//        if (correct) {
//            // 정답자에게 점수 추가
//            scores.put(guess, scores.getOrDefault(guess, 0) + 1);
//        }
//        return new MusicDto.GuessResponse(correct, correct ? "정답입니다!" : "오답입니다.");
//    }
//
//    // 게임 종료
//    public void endGame() {
//        isGameRunning = false;
//        currentMusicList.clear();
//        scores.clear();
//    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public GameRoom getGameRoomById(Long gameRoomId) {
        GameRoom gameRoom = redisTemplate.opsForValue().get("gameroom:" + gameRoomId);
        if (gameRoom == null) {
            throw new BusinessLogicException(ExceptionCode.GAME_ROOM_NOT_FOUND);
        }
        return gameRoom;
    }


    @Override
    public boolean endGame(GameRoom gameRoom, Map<Member, Integer> scores) {
        return true;
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
    public boolean guessAnswer(GameRoom gameRoom, Member member, String guess) {
        return false;
    }

    @Override
    public String getCurrentAnswer(GameRoom gameRoom) {
        return "";
    }

    public List<Music> getCurrentMusicList(GameRoom gameRoom) {
        return gameRoomMusics.get(gameRoom);
    }

}

package com.example.godgame.history.service;

import com.example.godgame.history.entity.GameHistory;
import com.example.godgame.history.entity.GameRoomHistory;
import com.example.godgame.history.repository.GameHistoryRepository;
import com.example.godgame.history.repository.GameRoomHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameHistoryService {
    @Autowired
    private GameHistoryRepository gameHistoryRepository;

    @Autowired
    private GameRoomHistoryRepository gameRoomHistoryRepository;

    public void saveGameHistories(GameHistory requestBody) {
        GameHistory gameHistory = new GameHistory();

        gameHistory.setMemberId(requestBody.getMemberId());
        gameHistory.setGameName(requestBody.getGameName());
        gameHistory.setScore(requestBody.getScore());

        // GameRoomHistory를 데이터베이스에서 가져오기
        GameRoomHistory gameRoomHistory = gameRoomHistoryRepository.findById(requestBody.getGameRoomHistory().getGameRoomHistoryId())
                .orElseThrow(() -> new RuntimeException("GameRoomHistory not found"));
        gameHistory.setGameRoomHistory(gameRoomHistory);

        // 게임 히스토리 저장
        gameHistoryRepository.save(gameHistory);
    }
}
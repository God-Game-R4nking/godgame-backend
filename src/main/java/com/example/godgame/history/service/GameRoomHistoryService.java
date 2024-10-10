package com.example.godgame.history.service;

import com.example.godgame.history.entity.GameHistory;
import com.example.godgame.history.entity.GameRoomHistory;
import com.example.godgame.history.repository.GameHistoryRepository;
import com.example.godgame.history.repository.GameRoomHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class GameRoomHistoryService {
  private GameRoomHistoryRepository gameRoomHistoryRepository;

    public GameRoomHistoryService(GameRoomHistoryRepository gameRoomHistoryRepository) {
        this.gameRoomHistoryRepository = gameRoomHistoryRepository;
    }

    public GameRoomHistory createGameRoomHistory(GameRoomHistory gameRoomHistory){
        return gameRoomHistoryRepository.save(gameRoomHistory);
    }
}

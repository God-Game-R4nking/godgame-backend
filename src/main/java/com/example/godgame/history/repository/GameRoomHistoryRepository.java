package com.example.godgame.history.repository;

import com.example.godgame.history.entity.GameRoomHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRoomHistoryRepository extends JpaRepository<GameRoomHistory,Long> {
}

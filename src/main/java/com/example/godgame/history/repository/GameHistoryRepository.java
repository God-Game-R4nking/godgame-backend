package com.example.godgame.history.repository;

import com.example.godgame.history.entity.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
}

package com.example.godgame.history.repository;

import com.example.godgame.history.entity.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
}

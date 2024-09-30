package com.example.godgame.gameroom.repository;

import com.example.godgame.gameroom.entity.GameRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRoomRepository extends MongoRepository<GameRoom, Long> {
}
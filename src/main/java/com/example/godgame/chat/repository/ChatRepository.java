package com.example.godgame.chat.repository;

import com.example.godgame.chat.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findByGameRoomId(String gameRoomId);
}
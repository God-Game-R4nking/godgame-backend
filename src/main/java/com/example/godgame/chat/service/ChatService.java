package com.example.godgame.chat.service;



import com.example.godgame.chat.entity.Chat;
import com.example.godgame.chat.repository.ChatRepository;
import com.example.godgame.counter.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private CounterService counterService;

    public Chat saveChat(Chat chat) {
        long newChatId = counterService.getNextSequence("chatId"); // 자동 생성된 ID 가져오기
        chat.setChatId(newChatId); // ID 설정
        chat.setCreatedAt(LocalDateTime.now()); // 생성 시간 설정
        return chatRepository.save(chat);
    }


    public List<Chat> getChatsByGameRoomId(String gameRoomId) {
        return chatRepository.findByGameRoomId(gameRoomId);
    }
}

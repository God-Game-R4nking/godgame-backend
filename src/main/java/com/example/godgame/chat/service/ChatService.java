package com.example.godgame.chat.service;



import com.example.godgame.chat.entity.Chat;
import com.example.godgame.chat.repository.ChatRepository;
import com.example.godgame.counter.service.CounterService;
import com.example.godgame.websocket.webchat.ChattingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private CounterService counterService;

    @Autowired
    private RedisTemplate<String, ChattingMessage> chattingMessageRedisTemplate;

    @Value("${chatting.messages.redis.key}")
    private String redisKey;

    public Chat saveChat(Chat chat) {
        long newChatId = counterService.getNextSequence("chatId"); // 자동 생성된 ID 가져오기
        chat.setChatId(newChatId); // ID 설정
        chat.setCreatedAt(LocalDateTime.now()); // 생성 시간 설정
        return chatRepository.save(chat);
    }



    public List<Chat> getChatsByGameRoomId(String gameRoomId) {
        return chatRepository.findByGameRoomId(gameRoomId);
    }

    public void saveAllChatsFromRedis(Long gameRoomId) {
        while (true) {
            ChattingMessage chattingMessage = chattingMessageRedisTemplate.opsForList().leftPop(redisKey + ":" + gameRoomId);
            if (chattingMessage == null) {
                break; // 더 이상 꺼낼 메시지가 없으면 종료
            }

            // ChattingMessage를 Chat으로 변환
            Chat chat = new Chat();
            chat.setChatId(counterService.getNextSequence("chatId")); // 새로운 ID 생성
            chat.setMemberId(chattingMessage.getMemberId()); // 멤버 ID 설정
            chat.setGameRoomId(chattingMessage.getGameRoomId()); // 게임룸 ID 설정
            chat.setContent(chattingMessage.getContent()); // 메시지 내용 설정
            chat.setCreatedAt(LocalDateTime.now()); // 생성 시간 설정

            saveChat(chat); // MongoDB에 저장
        }
    }



}

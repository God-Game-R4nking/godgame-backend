package com.example.godgame.websocket.service;


import com.example.godgame.websocket.webchat.ChattingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ChattingService {

    @Autowired(required = false)
    private RedisTemplate<String, ChattingMessage> redisTemplate;
    private final Logger log = LoggerFactory.getLogger(getClass());

    // Redis에 저장할 메시지의 키
    private static final String MESSAGE_KEY = "messages";

    // 메시지 저장 메서드
    public void saveMessage(ChattingMessage message){
        log.info("ChatService_saveMessage : " + message);

        // 각 채팅방에 대한 별도의 키 생성
        String roomKey = MESSAGE_KEY + ":" + message.getGameRoomId();

        // 해당 채팅방의 메시지 목록에 새로운 메시지 추가
        redisTemplate.opsForList().rightPush(roomKey, message);
    }

    // 전체 채팅방의 메시지 목록 조회 메서드
    public List<ChattingMessage> getMessages() {
        log.info("ChatService_getMessages");

        Long size = redisTemplate.opsForList().size(MESSAGE_KEY);
        if (size != null) {
            return redisTemplate.opsForList().range(MESSAGE_KEY, 0, size - 1);
        }
        return Collections.emptyList();
    }

    // 특정 채팅방의 메시지 목록 조회 메서드
    public List<ChattingMessage> getMessagesByRoom(String roomNo) {
        log.info("ChatService_getMessagesByRoom : "+ roomNo);
        String roomKey = MESSAGE_KEY + ":" + roomNo;

        Long size = redisTemplate.opsForList().size(roomKey);
        log.info("Room key: {}, Message count: {}", roomKey, size);

        if (size != null) {
            List<ChattingMessage> messages = redisTemplate.opsForList().range(roomKey, 0, size - 1);
            log.info("Messages retrieved: {}", messages);
            return messages;
        }
        log.info("No messages found.");
        return Collections.emptyList();
    }


}

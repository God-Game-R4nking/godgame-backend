package com.example.godgame.chat.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chats")
public class Chat {
    @Id
    private long  chatId;         // 채팅 ID
    private long memberId;         // 메시지 보낸 멤버 ID
    private String nickName;
    private long  gameRoomId;     // 게임 방 ID
    private String content;        // 메시지 내용
    private LocalDateTime createdAt; // 메시지 생성 시간
}
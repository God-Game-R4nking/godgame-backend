package com.example.godgame.websocket.webchat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChattingMessage {
    private long chatId;
    private long memberId;
    private String nickName;
    private long gameRoomId;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

}

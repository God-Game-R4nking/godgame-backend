package com.example.godgame.websocket.webchat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChattingMessage {
    private long chatId;
    private long memberId;
    private String nickName;
    private long gameRoomId;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonCreator // Jackson이 이 생성자를 사용하도록 지정
    public ChattingMessage(
            @JsonProperty("chatId") long chatId,
            @JsonProperty("memberId") long memberId,
            @JsonProperty("nickName") String nickName,
            @JsonProperty("gameRoomId") long gameRoomId,
            @JsonProperty("content") String content,
            @JsonProperty("createdAt") LocalDateTime createdAt
    ) {
        this.chatId = chatId;
        this.memberId = memberId;
        this.nickName = nickName;
        this.gameRoomId = gameRoomId;
        this.content = content;
        this.createdAt = createdAt;
    }

}

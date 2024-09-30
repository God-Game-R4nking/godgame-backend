package com.example.godgame.gameroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameRoomRequest {
    private String gameRoomName; // 게임 방 이름
    private long gameId;         // 게임 ID
    private Long memberId;       // 멤버 ID
}
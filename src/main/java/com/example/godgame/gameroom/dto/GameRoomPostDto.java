package com.example.godgame.gameroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameRoomPostDto {
    private String gameRoomName;
    private long memberId;
    private long gameId;
}


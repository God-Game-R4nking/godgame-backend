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
    private String gameName;
    private String gameRoomName;
    private int count;
    private long memberId;
    private int maxPopulation;
}


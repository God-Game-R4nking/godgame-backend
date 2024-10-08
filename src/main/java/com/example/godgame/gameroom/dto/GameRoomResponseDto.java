package com.example.godgame.gameroom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameRoomResponseDto {
    private long gameRoomId;
    private String gameName;
    private String gameRoomName;
    private int currentPopulation;
    private String gameRoomStatus;
    private int maxPopulation;
    private List<Long> memberIds;
}

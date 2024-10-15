package com.example.godgame.gameroom.dto;

import com.example.godgame.catchmind.entity.Catchmind;
import com.example.godgame.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameRoomResponseDto {
    private long gameRoomId;
    private String gameName;
    private int count;
    private String gameRoomName;
    private int currentPopulation;
    private String gameRoomStatus;
    private int maxPopulation;
    private List<Long> memberIds;
    private String roomManagerName;
    private boolean isGameRunning;
    private Map<Long, Integer> scores;
}

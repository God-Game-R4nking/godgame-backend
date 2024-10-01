package com.example.godgame.gameroom.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 레디스에서 사용할 게임룸 클래스
@Getter
@Setter
@NoArgsConstructor
public class GameRoom {
    private String gameRoomName;
    private String gameRoomPassword; // 비공개 방의 경우 필요
    private int maxPopulation;
    private int currentPopulation; // 현재 인원수
    private String gameRoomStatus; // 대기중, 게임중
    private String publicStatus; // 공개, 비공개
}


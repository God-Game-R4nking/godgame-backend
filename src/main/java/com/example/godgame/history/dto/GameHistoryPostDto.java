package com.example.godgame.history.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameHistoryPostDto {
    private long gameRoomHistoryId;
    private String gameName;
    private long memberId;
    private int score;
}
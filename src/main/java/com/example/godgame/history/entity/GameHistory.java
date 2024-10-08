package com.example.godgame.history.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
// 게임이 시작될 때 POST 요청으로 생성
// 게임이 종료 될 때 PATCH 요청으로 수정 하여 매 라운드가 끝날 떄 기록
public class GameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameHistoryId;

    @Column
    private long memberId;

    @Column
    private String gameName;

    @Column
    private int score;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_room_history_id")
    GameRoomHistory gameRoomHistory;

    public void updateScore(int newScore) {
        this.score = newScore;
        this.modifiedAt = LocalDateTime.now();
    }

}

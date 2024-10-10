package com.example.godgame.history.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class GameRoomHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameRoomHistoryId;

    @Column
    private int currentPopulation;

    @Column
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "gameRoomHistory", cascade = CascadeType.ALL) // 게임 히스토리와의 관계 설정
    private List<GameHistory> gameHistories = new ArrayList<>();


}

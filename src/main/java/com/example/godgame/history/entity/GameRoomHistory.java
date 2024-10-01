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
public class GameRoomHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameRoomHistoryId;

    @Column
    private int currentMember;

    @Column
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

}

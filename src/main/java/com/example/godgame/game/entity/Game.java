package com.example.godgame.game.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Game {
    @Id
    @Column(name = "game_name", nullable = false, length = 30)
    private String gameName;

    @Column
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus = GameStatus.ACTIVE;

    public enum GameStatus {
        ACTIVE("게임 서비스 중"),
        SUSPENDED("게임 일시정지"),
        TERMINATED("게임 서비스 단종");

        @Getter
        private String status;

        GameStatus(String status) {
            this.status = status;
        }
    }

}

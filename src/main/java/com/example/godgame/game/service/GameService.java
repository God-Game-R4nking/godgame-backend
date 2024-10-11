package com.example.godgame.game.service;

import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.member.entity.Member;

import java.util.Map;

public interface GameService {

    void initializeGameRoom(GameRoom gameRoom);
    boolean guessAnswer(Member member, String guess);
    String getCurrentAnswer();
    boolean guessAnswer(GameRoom gameRoom, Member member, String guess);
    String getCurrentAnswer(GameRoom gameRoom);
}

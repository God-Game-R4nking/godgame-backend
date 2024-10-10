package com.example.godgame.game.service;

import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.member.entity.Member;

public interface GameService {

    void initializeGameRoom(GameRoom gameRoom);
    void startGame(GameRoom gameRoom, int count);
    void endGame(GameRoom gameRoom);
    boolean guessAnswer(Member member, String guess);
    String getCurrentAnswer();

    boolean guessAnswer(GameRoom gameRoom, Member member, String guess);

    String getCurrentAnswer(GameRoom gameRoom);
}

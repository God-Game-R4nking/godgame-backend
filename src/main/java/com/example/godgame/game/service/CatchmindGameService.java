package com.example.godgame.game.service;

import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.member.entity.Member;

public class CatchmindGameService implements GameService {

    @Override
    public void initializeGameRoom(GameRoom gameRoom) {

    }

    public void startGame(GameRoom gameRoom, int count) {

    }

    @Override
    public void endGame(GameRoom gameRoom) {

    }

    @Override
    public boolean guessAnswer(Member member, String guess) {
        return false;
    }

    @Override
    public String getCurrentAnswer() {
        return "";
    }

    @Override
    public boolean guessAnswer(GameRoom gameRoom, Member member, String guess) {
        return false;
    }

    @Override
    public String getCurrentAnswer(GameRoom gameRoom) {
        return "";
    }
}

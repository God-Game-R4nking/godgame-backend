package com.example.godgame.game.service;

import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.member.entity.Member;

import java.util.Map;

public class ImageGameService implements GameService{
    @Override
    public void initializeGameRoom(GameRoom gameRoom) {

    }

    public boolean endGame(GameRoom gameRoom, Map<Member, Integer> scores) {
        return false;
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

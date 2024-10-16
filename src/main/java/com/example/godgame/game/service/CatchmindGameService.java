package com.example.godgame.game.service;

import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.member.entity.Member;
import com.example.godgame.websocket.webchat.ChattingMessage;

import java.util.Map;

public abstract class CatchmindGameService implements GameService {

    @Override
    public void initializeGameRoom(GameRoom gameRoom) {

    }

    public void startCatchmind(GameRoom gameRoom, int count) {

    }

    public void guessAnswer(GameRoom gameRoom, Member member, ChattingMessage parseChattingMessage) {

    }

    public void endGame(GameRoom gameRoom, Map<Long, Integer> scores) {
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

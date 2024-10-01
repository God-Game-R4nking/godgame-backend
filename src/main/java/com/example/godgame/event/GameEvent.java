//package com.example.godgame.event;
//
//import org.springframework.context.ApplicationEvent;
//
//import java.time.Clock;
//
//
//public class GameEvent extends ApplicationEvent {
//
//    private String roomName;
//    private Long memberId;
//    private Long gameId;
//    private String action;
//
//    public GameEvent(Object source, String roomName, Long memberId, Long gameId, String action) {
//        super(source);
//        this.roomName = roomName;
//        this.memberId = memberId;
//        this.gameId = gameId;
//        this.action = action;
//    }
//
//    public GameEvent(Object source, Clock clock, String roomName, Long memberId, Long gameId, String action) {
//        super(source, clock);
//        this.roomName = roomName;
//        this.memberId = memberId;
//        this.gameId = gameId;
//        this.action = action;
//    }
//
//    // Getters
//    public String getRoomName() {
//        return roomName;
//    }
//
//    public Long getMemberId() {
//        return memberId;
//    }
//
//    public Long getGameId() {
//        return gameId; // 게임 ID Getter 추가
//    }
//
//    public String getAction() {
//        return action;
//    }
//}
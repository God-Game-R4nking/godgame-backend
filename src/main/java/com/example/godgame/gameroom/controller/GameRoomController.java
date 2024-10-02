package com.example.godgame.gameroom.controller;

import com.example.godgame.gameroom.service.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game-rooms")
public class GameRoomController {
    @Autowired
    private GameRoomService gameRoomService;

    @PostMapping
    public ResponseEntity<Void> createGameRoom(@RequestParam String gameRoomName,
                                               @RequestParam(required = false) String gameRoomPassword,
                                               @RequestParam long memberId,
                                               @RequestParam boolean isPublic, // isPublic 매개변수 추가
                                               @RequestParam long gameId) { // gameId 매개변수 추가
        gameRoomService.createGameRoom(gameRoomName, gameRoomPassword, isPublic, memberId, gameId); // gameId도 넘겨줌
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{gameRoom-id}/join/{member-id}")
    public ResponseEntity<Void> joinGame(@PathVariable("gameRoom-id") long gameRoomId, @PathVariable("member-id") Long memberId) {
        if (gameRoomService.joinGame(gameRoomId, memberId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

//    @PostMapping("/{roomName}/start")
//    public ResponseEntity<Void> startGame(@PathVariable String roomName) {
//        gameRoomService.startGame(roomName);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/{roomName}/end")
//    public ResponseEntity<Void> endGame(@PathVariable String roomName) {
//        gameRoomService.endGame(roomName);
//        return ResponseEntity.ok().build();
//    }
}

package com.example.godgame.gameroom.controller;

import com.example.godgame.gameroom.dto.GameRoomRequest;
import com.example.godgame.gameroom.entity.GameRoom;
import com.example.godgame.gameroom.service.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gamerooms")
public class GameRoomController {
    @Autowired
    private GameRoomService gameRoomService;

    @PostMapping
    public ResponseEntity<GameRoom> createGameRoom(@RequestBody GameRoomRequest request) {
        long newId = gameRoomService.generateNewId(); // long 타입 ID 자동 생성
        GameRoom gameRoom = new GameRoom();
        gameRoom.setGameRoomId(newId);
        gameRoom.setGameId(request.getGameId());
        gameRoom.setGameRoomName(request.getGameRoomName());

        // 게임 방 생성 시 멤버 추가 (예: 첫 번째 멤버)
        if (request.getMemberId() != null) {
            gameRoom.addMember(request.getMemberId());
            gameRoom.setCurrentMembers(1); // 현재 인원 수를 1로 설정
        }

        GameRoom savedGameRoom = gameRoomService.saveGameRoom(gameRoom);
        return ResponseEntity.status(201).body(savedGameRoom);
    }

    @PostMapping("/{gameRoomId}/join/{memberId}")
    public ResponseEntity<String> joinGameRoom(@PathVariable long gameRoomId, @PathVariable long memberId) {
        boolean success = gameRoomService.joinGameRoom(gameRoomId, memberId);
        if (success) {
            return ResponseEntity.ok("Successfully joined the game room.");
        } else {
            return ResponseEntity.status(400).body("Failed to join the game room. Maximum capacity reached.");
        }
    }

    @PostMapping("/{gameRoomId}/leave/{memberId}")
    public ResponseEntity<String> leaveGameRoom(@PathVariable long gameRoomId, @PathVariable long memberId) {
        boolean success = gameRoomService.leaveGameRoom(gameRoomId, memberId);
        if (success) {
            return ResponseEntity.ok("Successfully left the game room.");
        } else {
            return ResponseEntity.status(404).body("Game room not found.");
        }
    }
}
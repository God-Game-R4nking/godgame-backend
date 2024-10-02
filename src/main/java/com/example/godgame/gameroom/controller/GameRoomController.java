package com.example.godgame.gameroom.controller;

import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.gameroom.dto.GameRoomPostDto;
import com.example.godgame.gameroom.mapper.GameRoomMapper;
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
    @Autowired
    private GameRoomMapper gameRoomMapper;

    @PostMapping
    public ResponseEntity<Void> createGameRoom(@RequestBody GameRoomPostDto requestBody) {
        if (requestBody == null) {
            throw new RuntimeException("Request body is null");
        }

        System.out.println("Received request body: " + requestBody); // 디버깅용 로그 추가

        GameRoom gameRoom = gameRoomMapper.gameRoomPostDtoToGameRoom(requestBody);
        if (gameRoom == null) {
            throw new RuntimeException("GameRoom object is null after mapping");
        }

        gameRoomService.createGameRoom(gameRoom);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{gameroom-id}/join/{member-id}")
    public ResponseEntity<Void> joinGame(@PathVariable("gameroom-id") long gameRoomId, @PathVariable("member-id") Long memberId) {
        if (gameRoomService.joinGame(gameRoomId, memberId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @DeleteMapping("/{gameroom-id}/leave/{member-id}")
    public ResponseEntity<String> leaveGame(
            @PathVariable("gameroom-id") long gameRoomId, @PathVariable("member-id") Long memberId) {

        boolean success = gameRoomService.leaveGame(gameRoomId, memberId);

        if (success) {
            return ResponseEntity.ok("Successfully left the game.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game room not found or member not in the game.");
        }
    }
}



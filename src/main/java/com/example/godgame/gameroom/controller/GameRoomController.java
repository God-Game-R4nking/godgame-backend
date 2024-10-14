package com.example.godgame.gameroom.controller;

import com.example.godgame.dto.SingleResponseDto;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.gameroom.dto.GameRoomPostDto;
import com.example.godgame.gameroom.dto.GameRoomResponseDto;
import com.example.godgame.gameroom.mapper.GameRoomMapper;
import com.example.godgame.gameroom.service.GameRoomService;
import com.example.godgame.websocket.session.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@RestController
@RequestMapping("/game-rooms")
public class GameRoomController {
    @Autowired
    private GameRoomService gameRoomService;
    @Autowired
    private GameRoomMapper gameRoomMapper;
    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @PostMapping
    public ResponseEntity<GameRoomResponseDto> createGameRoom(@RequestBody GameRoomPostDto requestBody) {
        if (requestBody == null) {
            throw new RuntimeException("Request body is null");
        }

        System.out.println("Received request body: " + requestBody); // 디버깅용 로그 추가

        GameRoom gameRoom = gameRoomMapper.gameRoomPostDtoToGameRoom(requestBody);
        if (gameRoom == null) {
            throw new RuntimeException("GameRoom object is null after mapping");
        }

        GameRoomResponseDto response = gameRoomService.createGameRoom(gameRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/{game-room-id}/join/{member-id}")
    public ResponseEntity<GameRoomResponseDto> joinGame(@PathVariable("game-room-id") long gameRoomId,
                                                        @PathVariable("member-id") Long memberId) {
        try {
//            WebSocketSession session = webSocketSessionManager.getSession(sessionId);
            GameRoomResponseDto response = gameRoomService.joinGame(gameRoomId, memberId);
            return ResponseEntity.ok(response);
        } catch (BusinessLogicException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 필요한 경우 메시지를 추가할 수 있어
        }
    }

    @PostMapping("{game-room-id}/start")
    public ResponseEntity startGame(@PathVariable("game-room-id") long gameRoomId, Authentication authentication) {
        try {
            int getCount = gameRoomService.getGameRoom(gameRoomId).getCount();
            gameRoomService.startGame(gameRoomId, getCount, authentication);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessLogicException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/{game-room-id}/leave/{member-id}")
    public ResponseEntity<GameRoomResponseDto> leaveGame(@PathVariable("game-room-id") long gameRoomId,
                                                         @PathVariable("member-id") Long memberId) {
//        if (sessionId == null || sessionId.isEmpty()) {
//            return ResponseEntity.badRequest().body(null);
//        }

        try {
//            WebSocketSession session = webSocketSessionManager.getSession(sessionId);
            GameRoomResponseDto response = gameRoomService.leaveGame(gameRoomId, memberId);
            return ResponseEntity.ok(response);
        } catch (BusinessLogicException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 필요한 경우 메시지를 추가할 수 있어
        }
    }

    @GetMapping
    public ResponseEntity getGameRooms(){
            List<GameRoomResponseDto> response = gameRoomService.getGameRooms();

            return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{game-room-id}")
    public ResponseEntity getGameRoom(@PathVariable("game-room-id") long gameRoomId){
        GameRoomResponseDto response = gameRoomService.getGameRoom(gameRoomId);

        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PostMapping("/{game-room-id}/end")
    public ResponseEntity endGame(@PathVariable("game-room-id") long gameRoomId) {
        try {
            GameRoomResponseDto gameRoomName = gameRoomService.getGameRoom(gameRoomId);
            gameRoomService.endGame(gameRoomName.getGameRoomName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessLogicException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 필요한 경우 메시지를 추가할 수 있어
        }
    }
}



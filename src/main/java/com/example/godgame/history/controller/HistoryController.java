package com.example.godgame.history.controller;

import com.example.godgame.history.dto.GameHistoryPostDto;
import com.example.godgame.history.dto.GameRoomHistoryPostDto;
import com.example.godgame.history.entity.GameHistory;
import com.example.godgame.history.entity.GameRoomHistory;
import com.example.godgame.history.mapper.GameHistoryMapper;
import com.example.godgame.history.mapper.GameRoomHistoryMapper;
import com.example.godgame.history.service.GameHistoryService;
import com.example.godgame.history.service.GameRoomHistoryService;
import com.example.godgame.member.dto.MemberDto;
import com.example.godgame.member.entity.Member;
import com.example.godgame.utils.UriCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/history")
public class HistoryController {
    private GameRoomHistoryService gameRoomHistoryService;
    private GameHistoryService gameHistoryService;
    private GameHistoryMapper gameHistoryMapper;
    private GameRoomHistoryMapper gameRoomHistoryMapper;

    public HistoryController(GameRoomHistoryService gameRoomHistoryService, GameHistoryService gameHistoryService, GameHistoryMapper gameHistoryMapper, GameRoomHistoryMapper gameRoomHistoryMapper) {
        this.gameRoomHistoryService = gameRoomHistoryService;
        this.gameHistoryService = gameHistoryService;
        this.gameHistoryMapper = gameHistoryMapper;
        this.gameRoomHistoryMapper = gameRoomHistoryMapper;
    }

    @PostMapping("/game-rooms")
    public ResponseEntity createGameRoomHistory(@Valid @RequestBody GameRoomHistoryPostDto requestBody) {

        GameRoomHistory gameRoomHistory = gameRoomHistoryMapper.gameRoomHistoryPostDtoToGameRoomHistory(requestBody);
        GameRoomHistory createdGameRoomHistory =  gameRoomHistoryService.createGameRoomHistory(gameRoomHistory);

        URI location = UriCreator.createUri("/history", createdGameRoomHistory.getGameRoomHistoryId());

        return ResponseEntity.created(location).build();
    }

    @PostMapping
    public ResponseEntity<Void> createGameHistory(@Valid @RequestBody GameHistoryPostDto requestBody) {
        GameHistory gameHistory = gameHistoryMapper.gameHistoryPostDtoToGameHistory(requestBody);
        gameHistoryService.saveGameHistories(gameHistory);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
    }
}

//package com.example.godgame.music.controller;
//
//import com.example.godgame.gameroom.GameRoom;
//import com.example.godgame.music.dto.MusicDto;
//import com.example.godgame.music.service.MusicService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@Slf4j
//@Validated
//@RequestMapping("/music")
//public class MusicController {
//
//    private final MusicService musicService;
//
//    @Autowired
//    public MusicController(MusicService musicService) {
//        this.musicService = musicService;
//    }
//
//    // 게임 시작
//    @PostMapping("/start")
//    public MusicDto.Response startGame(@RequestBody MusicDto.Request request, @RequestParam long gameRoomId) {
//
//        GameRoom gameRoom = musicService.getGameRoomById(gameRoomId);
//        musicService.startGame(gameRoom, request.getCount(), request.getEra());
//
//        return ;
//    }
//
//    // 정답 제출
//    @PostMapping("/guess")
//    public MusicDto.GuessResponse guessAnswer(@RequestBody MusicDto.GuessRequest request) {
//        return musicService.guessAnswer(request.getGuess());
//    }
//
//    // 게임 종료
//    @PostMapping("/end")
//    public void endGame() {
//        musicService.endGame();
//    }
//
//    // 점수 확인
//    @GetMapping("/scores")
//    public Map<String, Integer> getScores() {
//        return musicService.getScores();
//    }
//}
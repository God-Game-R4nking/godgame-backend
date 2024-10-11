//package com.example.godgame.catchmind.controller;
//
//import com.example.godgame.catchmind.dto.CatchmindDto;
//import com.example.godgame.catchmind.mapper.CatchmindMapper;
//import com.example.godgame.catchmind.service.CatchmindService;
//import com.example.godgame.dto.SingleResponseDto;
//import com.example.godgame.gameroom.GameRoom;
//import com.example.godgame.member.entity.Member;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@RestController
//@Slf4j
//@Validated
//@RequestMapping("/catchmind")
//public class CatchmindController {
//
//    private final CatchmindService catchmindService;
//    private final CatchmindMapper catchmindMapper;
//
//    public CatchmindController(CatchmindService catchmindService, CatchmindMapper catchmindMapper) {
//        this.catchmindService = catchmindService;
//        this.catchmindMapper = catchmindMapper;
//    }
//
//    // 게임 시작
//    @PostMapping("/start")
//    public ResponseEntity<CatchmindDto.Response> startGame(
//            @RequestBody @Valid CatchmindDto.Request request,
//            @RequestParam Long gameRoomId) {
//
//        GameRoom gameRoom = catchmindService.getGameRoomById(gameRoomId);
//        catchmindService.startCatchmind(gameRoom, request.getCount());
//
//        // 게임 상태와 초기 정보를 반환
//        Map<String, Integer> scores = catchmindService.getMembers(gameRoom).stream()
//                .collect(Collectors.toMap(Member::getId, member -> catchmindService.getScoreForMember(gameRoom, member)));
//
//        CatchmindDto.Response response = new CatchmindDto.Response(
//                "STARTED",
//                catchmindService.getCurrentAnswer(gameRoom),
//                60,  // 초기 라운드 타이머
//                scores
//        );
//
//        return ResponseEntity.ok(response);
//    }
//
//    // 게임 종료
//    @PostMapping("/end")
//    public ResponseEntity<CatchmindDto.Response> endGame(@RequestParam Long gameRoomId) {
//        GameRoom gameRoom = catchmindService.getGameRoomById(gameRoomId);
//        catchmindService.endGame(gameRoom);
//
//        CatchmindDto.Response response = new CatchmindDto.Response(
//                "ENDED",
//                null,
//                0,
//                null
//        );
//
//        return ResponseEntity.ok(response);
//    }
//
//    // 사용자가 정답을 추측하는 메서드
//    @PostMapping("/guess")
//    public ResponseEntity<CatchmindDto.GuessResponse> guessAnswer(
//            @RequestBody @Valid CatchmindDto.GuessRequest request,
//            @RequestParam Long gameRoomId,
//            @RequestParam Long memberId) {
//
//        Member member = catchmindService.getMemberById(memberId);
//        GameRoom gameRoom = catchmindService.getGameRoomById(gameRoomId);
//
//        boolean correct = catchmindService.guessAnswer(gameRoom, member, request.getGuess());
//
//        CatchmindDto.GuessResponse response = new CatchmindDto.GuessResponse(
//                correct,
//                correct ? "정답입니다!" : "틀렸습니다!"
//        );
//
//        return ResponseEntity.ok(response);
//    }
//
//    // 게임 상태 확인
//    @GetMapping("/status")
//    public ResponseEntity<CatchmindDto.Response> getGameStatus(@RequestParam Long gameRoomId) {
//        GameRoom gameRoom = catchmindService.getGameRoomById(gameRoomId);
//        String currentAnswer = catchmindService.getCurrentAnswer(gameRoom);
//        int roundTime = catchmindService.getRoundTime(gameRoom);
//
//        Map<String, Integer> scores = catchmindService.getMembers(gameRoom).stream()
//                .collect(Collectors.toMap(Member::getMemberName, member -> catchmindService.getScoreForMember(gameRoom, member)));
//
//        CatchmindDto.Response response = new CatchmindDto.Response(
//                "RUNNING",
//                currentAnswer,
//                roundTime,
//                scores
//        );
//
//        return ResponseEntity.ok(response);
//    }
//}
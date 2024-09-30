package com.example.godgame.gameroom.service;

import com.example.godgame.counter.service.CounterService;
import com.example.godgame.gameroom.entity.GameRoom;
import com.example.godgame.gameroom.repository.GameRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class GameRoomService {
    @Autowired
    private GameRoomRepository gameRoomRepository;

    // TODO : GAME JPA 레퍼지토리 의존성 주입 후 createGameRoom()메서드 활용

    // 게임룸을 생성할 때 ID를 long 타입으로 관리하기 위해서 만든 카운터클래스 DI
    @Autowired
    private CounterService counterService;

    private static final int MAX_MEMBERS = 8;



    public long generateNewId() {
        return counterService.getNextSequence("gameRoomId"); // ID 생성
    }


    public GameRoom saveGameRoom(GameRoom gameRoom) {
        return gameRoomRepository.save(gameRoom);
    }


    public List<GameRoom> getAllGameRooms() {
        return gameRoomRepository.findAll();
    }

    public boolean joinGameRoom(long gameRoomId, long memberId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameRoomId).orElse(null);
        if (gameRoom != null && gameRoom.getCurrentMembers() < MAX_MEMBERS) {
            gameRoom.addMember(memberId); // 멤버 추가
            gameRoomRepository.save(gameRoom); // 업데이트된 게임 방 저장
            return true; // 성공적으로 참여
        }
        return false; // 참여 실패
    }

    public boolean leaveGameRoom(long gameRoomId, long memberId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameRoomId).orElse(null);
        if (gameRoom != null) {
            gameRoom.setCurrentMembers(gameRoom.getCurrentMembers() - 1);
            if (gameRoom.getCurrentMembers() <= 0) {
                gameRoomRepository.delete(gameRoom); // 인원 수가 0이면 게임 방 삭제
            } else {
                gameRoomRepository.save(gameRoom);
            }
            return true; // 성공적으로 나감
        }
        return false; // 나가기 실패
    }
}
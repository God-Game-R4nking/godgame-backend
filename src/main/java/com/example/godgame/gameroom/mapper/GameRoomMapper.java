package com.example.godgame.gameroom.mapper;

import com.example.godgame.gameroom.GameRoom;
import com.example.godgame.gameroom.dto.GameRoomPostDto;
import com.example.godgame.gameroom.dto.GameRoomResponseDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GameRoomMapper {
    default GameRoom gameRoomPostDtoToGameRoom(GameRoomPostDto requestBody) {
        if (requestBody == null) {
            throw new IllegalArgumentException("requestBody must not be null");
        }

        System.out.println("Received requestBody: " + requestBody);

        GameRoom gameRoom = new GameRoom();

        gameRoom.setGameRoomName(requestBody.getGameRoomName());
        gameRoom.setGameName(requestBody.getGameName());
        gameRoom.setMaxPopulation(requestBody.getMaxPopulation());
        gameRoom.setCurrentPopulation(1);

        List<Long> memberIds = new ArrayList<>();
        memberIds.add(requestBody.getMemberId());
        gameRoom.setMemberIds(memberIds);

        System.out.println("Mapped GameRoom: " + gameRoom);

        return gameRoom;
    }

    GameRoomResponseDto gameRoomToGameRoomResponseDto(GameRoom gameRoom);
    List<GameRoomResponseDto> gameRoomsToGameRoomResponseDtos(List<GameRoom> gameRooms);
}


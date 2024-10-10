package com.example.godgame.history.mapper;

import com.example.godgame.history.dto.GameRoomHistoryPostDto;
import com.example.godgame.history.entity.GameRoomHistory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameRoomHistoryMapper {
    GameRoomHistory gameRoomHistoryPostDtoToGameRoomHistory(GameRoomHistoryPostDto requestBody);
}

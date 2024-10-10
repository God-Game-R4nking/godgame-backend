package com.example.godgame.history.mapper;

import com.example.godgame.history.dto.GameHistoryPostDto;
import com.example.godgame.history.dto.GameRoomHistoryPostDto;
import com.example.godgame.history.entity.GameHistory;
import com.example.godgame.history.entity.GameRoomHistory;
import org.hibernate.annotations.Source;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameHistoryMapper {

    @Mapping(source = "gameRoomHistoryId", target = "gameRoomHistory.gameRoomHistoryId")
    GameHistory gameHistoryPostDtoToGameHistory(GameHistoryPostDto requestBody);
}

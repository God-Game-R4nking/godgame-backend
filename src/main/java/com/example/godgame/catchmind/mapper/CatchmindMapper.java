package com.example.godgame.catchmind.mapper;

import com.example.godgame.catchmind.dto.CatchmindResponseDto;
import com.example.godgame.catchmind.entity.Catchmind;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatchmindMapper {
    CatchmindResponseDto catchmindToCatchmindResponseDto(Catchmind catchmind);
    List<CatchmindResponseDto> catchmindToCatchmindResponseDtos(List<Catchmind> catchminds);
}

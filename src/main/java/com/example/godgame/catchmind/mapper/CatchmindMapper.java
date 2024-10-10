package com.example.godgame.catchmind.mapper;

import com.example.godgame.catchmind.dto.CatchmindDto;
import com.example.godgame.catchmind.entity.Catchmind;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatchmindMapper {
    CatchmindDto.Response catchmindToCatchmindResponseDto(Catchmind catchmind);
    List<CatchmindDto.Response> catchmindToCatchmindResponseDtos(List<Catchmind> catchminds);
}

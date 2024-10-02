package com.example.godgame.ranking.mapper;

import com.example.godgame.ranking.dto.RankingDto;
import com.example.godgame.ranking.entity.Ranking;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RankingMapper {
    RankingDto.Response rankingToRankingResponseDto(Ranking ranking);
    List<RankingDto.Response> rankingToRankingResponseDtos(List<Ranking> rankings);
}

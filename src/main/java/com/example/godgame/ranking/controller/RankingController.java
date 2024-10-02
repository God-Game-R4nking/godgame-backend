package com.example.godgame.ranking.controller;


import com.example.godgame.dto.SingleResponseDto;
import com.example.godgame.ranking.mapper.RankingMapper;
import com.example.godgame.ranking.service.RankingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Validated
@RequestMapping("/ranking")
public class RankingController {
    private final RankingService rankingService;
    private final RankingMapper mapper;

    public RankingController(RankingService rankingService, RankingMapper mapper) {
        this.rankingService = rankingService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity getRankings(){

        return new ResponseEntity(new SingleResponseDto<>(
                mapper.rankingToRankingResponseDtos(rankingService.findAllRanking())), HttpStatus.OK);

    }

}

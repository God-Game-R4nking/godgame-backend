package com.example.godgame.ranking.service;


import com.example.godgame.ranking.entity.Ranking;
import com.example.godgame.ranking.repository.RankingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RankingService {

    private final RankingRepository rankingRepository;

    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public List<Ranking> findAllRanking(){
        return rankingRepository.findAll();
    }

}

package com.example.godgame.catchmind.service;

import com.example.godgame.Image.country.entity.CountryImage;
import com.example.godgame.Image.country.repository.CountryImageRepository;
import com.example.godgame.catchmind.entity.Catchmind;
import com.example.godgame.catchmind.repository.CatchmindRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CatchmindService {

    private final CatchmindRepository catchmindRepository;

    public CatchmindService(CatchmindRepository catchmindRepository) {
        this.catchmindRepository = catchmindRepository;
    }

    public List<Catchmind> findRandomWord(int count){
        return catchmindRepository.findRandomCatchminds(count);
    }
}

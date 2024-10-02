package com.example.godgame.music.service;


import com.example.godgame.music.entity.Music;
import com.example.godgame.music.repository.MusicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MusicService {

    private final MusicRepository musicRepository;

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public List<Music> findRandomMusic(int count, int era){
        return musicRepository.findRandomMusics(count, era);
    }
}

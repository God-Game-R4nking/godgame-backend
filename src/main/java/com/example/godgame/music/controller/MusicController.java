package com.example.godgame.music.controller;


import com.example.godgame.dto.SingleResponseDto;
import com.example.godgame.music.dto.MusicResponseDto;
import com.example.godgame.music.mapper.MusicMapper;
import com.example.godgame.music.service.MusicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/music")
public class MusicController {
    private final MusicService musicService;
    private final MusicMapper mapper;

    public MusicController(MusicService musicService, MusicMapper mapper) {
        this.musicService = musicService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity getMusics(@RequestParam int count, @RequestParam String era) {

        List<MusicResponseDto> musicResponseDtos = mapper.musicToMusicResponseDtos(musicService.findRandomMusic(count, era));

        return new ResponseEntity(new SingleResponseDto<>(musicResponseDtos), HttpStatus.OK);
    }
}

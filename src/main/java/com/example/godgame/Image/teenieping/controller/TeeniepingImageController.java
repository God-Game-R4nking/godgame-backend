package com.example.godgame.Image.teenieping.controller;


import com.example.godgame.Image.teenieping.dto.TeeniepingImageResponseDto;
import com.example.godgame.Image.teenieping.mapper.TeeniepingImageMapper;
import com.example.godgame.Image.teenieping.service.TeeniepingImageService;
import com.example.godgame.dto.SingleResponseDto;
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
@RequestMapping("/teenieping")
public class TeeniepingImageController {
    private final TeeniepingImageMapper mapper;
    private final TeeniepingImageService teeniepingImageService;

    public TeeniepingImageController(TeeniepingImageMapper mapper, TeeniepingImageService teeniepingImageService) {
        this.mapper = mapper;
        this.teeniepingImageService = teeniepingImageService;
    }

    @GetMapping()
    public ResponseEntity getTeeniepingImages(@RequestParam int count) {

        List<TeeniepingImageResponseDto> response = mapper.teeniepingImageToTeeniepingImageResponseDtos(teeniepingImageService.findRandomImage(count));

        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }
}

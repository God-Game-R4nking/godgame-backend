package com.example.godgame.Image.people.controller;



import com.example.godgame.Image.people.dto.PeopleImageResponseDto;
import com.example.godgame.Image.people.mapper.PeopleImageMapper;
import com.example.godgame.Image.people.service.PeopleImageService;
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
@RequestMapping("/people")
public class PeopleImageController {

    private final PeopleImageMapper mapper;
    private final PeopleImageService peopleImageService;

    public PeopleImageController(PeopleImageMapper mapper, PeopleImageService peopleImageService) {
        this.mapper = mapper;
        this.peopleImageService = peopleImageService;
    }

    @GetMapping()
    public ResponseEntity getPeopleImages(@RequestParam int count) {

        List<PeopleImageResponseDto> response = mapper.peopleImagesToPeopleImageResponseDtos(peopleImageService.findRandomImage(count));

        return new ResponseEntity(new SingleResponseDto<>(response), HttpStatus.OK);
    }
}

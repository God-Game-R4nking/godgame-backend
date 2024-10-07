package com.example.godgame.catchmind.controller;

import com.example.godgame.Image.country.dto.CountryImageResponseDto;
import com.example.godgame.Image.country.entity.CountryImage;
import com.example.godgame.Image.country.mapper.CountryImageMapper;
import com.example.godgame.Image.country.repository.CountryImageRepository;
import com.example.godgame.Image.country.service.CountryImageService;
import com.example.godgame.catchmind.dto.CatchmindResponseDto;
import com.example.godgame.catchmind.mapper.CatchmindMapper;
import com.example.godgame.catchmind.service.CatchmindService;
import com.example.godgame.dto.SingleResponseDto;
import com.example.godgame.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/catchmind")
public class CatchmindController {

    private final CatchmindService catchmindService;
    private final CatchmindMapper mapper;

    public CatchmindController(CatchmindService catchmindService, CatchmindMapper mapper) {
        this.catchmindService = catchmindService;
        this.mapper = mapper;
    }

    @GetMapping()
    public ResponseEntity getCatchminds(@RequestParam int count) {

        List<CatchmindResponseDto> catchmindResponseDtos = mapper.catchmindToCatchmindResponseDtos(catchmindService.findRandomWord(count));

        return new ResponseEntity(new SingleResponseDto<>(catchmindResponseDtos), HttpStatus.OK);
    }
}

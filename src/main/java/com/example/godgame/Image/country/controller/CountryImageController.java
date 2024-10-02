package com.example.godgame.Image.country.controller;


import com.example.godgame.Image.country.dto.CountryImageResponseDto;
import com.example.godgame.Image.country.entity.CountryImage;
import com.example.godgame.Image.country.mapper.CountryImageMapper;
import com.example.godgame.Image.country.repository.CountryImageRepository;
import com.example.godgame.Image.country.service.CountryImageService;
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
@RequestMapping("/country")
public class CountryImageController {
    private final CountryImageService countryImageService;
    private final CountryImageMapper mapper;

    public CountryImageController(CountryImageService countryImageService, CountryImageMapper mapper) {
        this.countryImageService = countryImageService;
        this.mapper = mapper;
    }

    @GetMapping()
    public ResponseEntity getCountryImages(@RequestParam int count) {

        List<CountryImageResponseDto> countryImageResponseDtos = mapper.countryImageToCountryImageResponseDtos(countryImageService.findRandomImage(count));

        return new ResponseEntity(new SingleResponseDto<>(countryImageResponseDtos), HttpStatus.OK);
    }
}

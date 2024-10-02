package com.example.godgame.Image.country.mapper;

import com.example.godgame.Image.country.dto.CountryImageResponseDto;
import com.example.godgame.Image.country.entity.CountryImage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryImageMapper {
    CountryImageResponseDto countryImageToCountryImageResponseDto(CountryImage countryImage);
    List<CountryImageResponseDto> countryImageToCountryImageResponseDtos(List<CountryImage> countryImages);
}

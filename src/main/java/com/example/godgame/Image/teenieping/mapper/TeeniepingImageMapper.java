package com.example.godgame.Image.teenieping.mapper;


import com.example.godgame.Image.teenieping.dto.TeeniepingImageResponseDto;
import com.example.godgame.Image.teenieping.entity.TeeniepingImage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeeniepingImageMapper {

    TeeniepingImageResponseDto teeniepingImageToTeeniepingResponseDto(TeeniepingImage teeniepingImage);
    List<TeeniepingImageResponseDto> teeniepingImageToTeeniepingImageResponseDtos(List<TeeniepingImage> teeniepingImages);
}

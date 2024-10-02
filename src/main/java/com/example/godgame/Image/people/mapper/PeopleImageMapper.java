package com.example.godgame.Image.people.mapper;

import com.example.godgame.Image.people.dto.PeopleImageResponseDto;
import com.example.godgame.Image.people.entity.PeopleImage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PeopleImageMapper {
    PeopleImageResponseDto peopleImageToPeopleImageResponseDto(PeopleImage peopleImage);
    List<PeopleImageResponseDto> peopleImagesToPeopleImageResponseDtos(List<PeopleImage> peopleImages);

}

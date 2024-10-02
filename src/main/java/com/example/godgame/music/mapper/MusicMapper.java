package com.example.godgame.music.mapper;

import com.example.godgame.music.dto.MusicResponseDto;
import com.example.godgame.music.entity.Music;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MusicMapper {
    MusicResponseDto musicToMusicResponseDto(Music music);
    List<MusicResponseDto> musicToMusicResponseDtos(List<Music> musics);
}

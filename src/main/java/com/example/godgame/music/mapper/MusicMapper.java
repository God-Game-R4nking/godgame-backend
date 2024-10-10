package com.example.godgame.music.mapper;

import com.example.godgame.music.dto.MusicDto;
import com.example.godgame.music.entity.Music;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MusicMapper {
    MusicDto.Response musicToMusicResponseDto(Music music);
    List<MusicDto.Response> musicToMusicResponseDtos(List<Music> musics);
}

package com.example.godgame.music.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicResponseDto {
    private String title;
    private String singer;
    private String musicLink;
    private int era;
}

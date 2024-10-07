package com.example.godgame.music.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "music")
public class Music {

    @Id
    private long musicId;

    @Column
    private String title;

    @Column
    private String singer;

    @Column(name = "music_link")
    private String musicLink;

    @Column
    private String era;

}

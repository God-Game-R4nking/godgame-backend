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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long musicId;

    @Column
    private String title;

    @Column
    private String singer;

    @Column
    private String musicLink;

    @Column
    private int era;

}

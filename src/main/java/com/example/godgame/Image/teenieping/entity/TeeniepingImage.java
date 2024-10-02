package com.example.godgame.Image.teenieping.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teenieping_image")
public class TeeniepingImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long teeniepingImageId;

    @Column
    private String teeniepingName;

    @Column
    private String teeniepingImageLink;
}

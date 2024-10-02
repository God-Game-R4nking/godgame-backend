package com.example.godgame.Image.people.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "people_image")
public class PeopleImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long peopleImageId;

    @Column
    private String peopleName;

    @Column
    private String peopleImageLink;
}

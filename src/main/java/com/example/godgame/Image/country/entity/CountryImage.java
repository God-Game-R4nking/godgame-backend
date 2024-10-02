package com.example.godgame.Image.country.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "country_image")
public class CountryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long countryImageId;

    @Column
    private String countryName;

    @Column
    private String countryImageLink;

}

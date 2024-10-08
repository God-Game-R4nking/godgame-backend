package com.example.godgame.catchmind.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "catchmind")
public class Catchmind {

    @Id
    private long catchmindId;

    @Column
    private String word;
}

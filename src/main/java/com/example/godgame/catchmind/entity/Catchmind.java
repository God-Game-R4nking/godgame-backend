package com.example.godgame.catchmind.entity;

import com.example.godgame.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "catchmind")
public class Catchmind {

    @Id
    @Column(name = "catchmind_id")
    private long catchmindId;

    @Column
    private String word;
}

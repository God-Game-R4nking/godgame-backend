package com.example.godgame.board.entity;

import com.example.godgame.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long viewId;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID", nullable = false)
    @JsonBackReference
    private Board board;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    @JsonBackReference
    private Member member;
}

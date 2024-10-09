package com.example.godgame.board.repository;

import com.example.godgame.board.entity.Board;
import com.example.godgame.board.entity.View;
import com.example.godgame.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
    boolean existsByBoardAndMember(Board board, Member member);
}

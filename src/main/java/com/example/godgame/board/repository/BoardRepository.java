package com.example.godgame.board.repository;

import com.example.godgame.board.entity.Board;
import com.example.godgame.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByTitleContainingAndBoardStatusNot(String title, Board.BoardStatus commentStatus, Pageable pageable);
    Page<Board> findByContentContainingAndBoardStatusNot(String content, Board.BoardStatus commentStatus, Pageable pageable);
    Page<Board> findAllByBoardStatusNot(Board.BoardStatus commentStatus, Pageable pageable);
}

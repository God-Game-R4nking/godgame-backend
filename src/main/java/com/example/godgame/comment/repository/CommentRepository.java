package com.example.godgame.comment.repository;

import com.example.godgame.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.board.id = :boardId AND c.commentStatus = 'COMMENT_REGISTERED'")
    Page<Comment> findByBoardId(@Param("boardId") long boardId, Pageable pageable);
}

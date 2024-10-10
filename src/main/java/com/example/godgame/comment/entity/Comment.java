package com.example.godgame.comment.entity;

import com.example.godgame.board.entity.Board;
import com.example.godgame.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID", nullable = false)
    @JsonBackReference
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    @JsonBackReference
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentStatus commentStatus = CommentStatus.COMMENT_REGISTERED;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();

    public enum CommentStatus {
        COMMENT_REGISTERED("등록 댓글"),
        COMMENT_DELETED("삭제 댓글");

        @Getter
        private String status;

        CommentStatus(String status) {
            this.status = status;
        }
    }
}

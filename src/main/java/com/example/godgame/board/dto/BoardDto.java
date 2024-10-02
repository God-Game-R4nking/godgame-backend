package com.example.godgame.board.dto;

import com.example.godgame.board.entity.Board;
import com.example.godgame.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {

        @NotBlank
        private String title;

        @NotBlank
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {

        private long boardId;

        @NotNull
        private String title;

        @NotNull
        private String content;

        @NotNull
        private Board.BoardStatus boardStatus;

        public void setBoardId(long boardId) { this.boardId = boardId;}
    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long boardId;
        private String title;
        private String content;
        private Board.BoardStatus boardStatus;
        private long memberId;
        private List<CommentResponseDto> comments;
        private int currentPage;
        private int totalPages;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;

        public String setBoardStatus() {return boardStatus.getStatus();}
        public void setComments(List<CommentResponseDto> comments) {this.comments = comments;}
        public void setCurrentPage(int currentPage) {this.currentPage = currentPage;}
        public void setTotalPages(int totalPages) {this.totalPages = totalPages;}
    }

    public static class CommentResponseDto {
        private long commentId;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}

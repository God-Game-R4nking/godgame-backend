package com.example.godgame.board.dto;

import com.example.godgame.board.entity.Board;
import com.example.godgame.comment.entity.Comment;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BoardDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private Long memberId;
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
        private List<Comment> comments;

        public String getBoardStatus() {return boardStatus.getStatus();}

    }
}

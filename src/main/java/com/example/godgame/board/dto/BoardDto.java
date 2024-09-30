package com.example.godgame.board.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BoardDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        @NotNull
        private String boardId;

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
        private String boardStatus;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private long boardId;
        private String title;
        private String content;
        private String boardStatus;
        private long memberId;
        private List<Comment> comments;

    }
}

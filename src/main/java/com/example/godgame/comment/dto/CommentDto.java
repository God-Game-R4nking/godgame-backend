package com.example.godgame.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        @Column(nullable = false)
        private String commentContent;

        @Column(nullable = false)
        private long boardId;

        @Column(nullable = false)
        private long memberId;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private long commentId;

        private String commentContent;

        public void setCommentId(long commentId) {this.commentId = commentId;}
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {

        private long commentId;
        private long boardId;
        private long memberId;
        private String commentContent;
        private String nickName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime modifiedAt;
    }
}

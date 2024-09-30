package com.example.godgame.exception;

import lombok.Getter;

public enum ExceptionCode {
    BOARD_NOT_FOUND(404, "Board not found"),
    QUESTION_EXISTS(409, "Question exists"),
    QUESTION_DELETE_NOT_PERMISSION(404, "Question delete not permission"),
    BOARD_NOT_VIEWABLE(404, "Board not viewable"),
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    BOARD_DELETE_NOT_AVAILABLE(409, "Board delete not available"),
    COMMENT_EXISTS(409, "Comment exists"),
    LIKE_NOT_FOUND(404, "Like not found");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message){
        this.status = code;
        this.message = message;
    }
}

package com.example.godgame.exception;

import lombok.Getter;

public enum ExceptionCode {
    BOARD_NOT_FOUND(404, "Board not found"),
    QUESTION_EXISTS(409, "Question exists"),
    QUESTION_NOT_AVAILABLE(409, "Question not available"),
    QUESTION_DELETE_NOT_PERMISSION(404, "Question delete not permission"),
    BOARD_NOT_VIEWABLE(404, "Board not viewable"),
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    MEMBER_NOT_ALLOWED(404, "Member not allowed"),
    NEED_MEMBERS(404, "Need members"),
    COMMENT_EXISTS(409, "Comment exists"),
    COMMENT_ALREADY_DELETED(404, "Comment already deleted"),
    LIKE_NOT_FOUND(404, "Like not found"),
    LEAVE_FAIL(404, "Game room leave fail"),
    GAME_ALREADY_START(404,"Game Already Start"),
    GAME_ROOM_JOIN_ERROR(404,"Game room or Member not found"),
    GAME_NOT_FOUND(404, "Game not found"),
    GAME_ROOM_NOT_FOUND(404, "Game room not found"),
    GAME_NOT_PROCEED(404, "Game not proceed"),
    MUSIC_NOT_FOUND(404, "Music not found"),
    NEED_MORE_MEMBER(404,"Need more member"),
    BAN_EXISTS(409,"Ban exists"),
    BAN_NOT_FOUND(404, "Ban not found"),
    BAN_DELETE_NOT_ALLOWED(404, "Ban delete not allowed"),
    PASSWORD_WRONG(400, "PASSWORD WRONG"),
    NICKNAME_EXISTS(409, "NickName exists");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message){
        this.status = code;
        this.message = message;
    }
}

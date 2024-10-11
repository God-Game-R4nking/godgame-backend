package com.example.godgame.board.mapper;

import com.example.godgame.board.dto.BoardDto;
import com.example.godgame.board.entity.Board;
import com.example.godgame.comment.entity.Comment;
import com.example.godgame.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
//    @Mapping(source = "memberId",target = "member.memberId")
    Board boardPostDtoToBoard(BoardDto.Post postDto);
    Board boardPatchDtoToBoard(BoardDto.Patch patchDto);
    @Mapping(source = "member.memberId",target = "memberId")
    @Mapping(source = "member.nickName",target = "nickName")
    default BoardDto.Response boardToResponseDto(Board board) {
        if ( board == null ) {
            return null;
        }

        long memberId = 0L;
        String nickName = null;
        long boardId = 0L;
        String title = null;
        String content = null;
        Board.BoardStatus boardStatus = null;
        int viewCount = 0;
        LocalDateTime createdAt = null;
        LocalDateTime modifiedAt = null;

        memberId = boardMemberMemberId( board );
        nickName = boardMemberNickName( board );
        boardId = board.getBoardId();
        title = board.getTitle();
        content = board.getContent();
        boardStatus = board.getBoardStatus();
        viewCount = board.getViewCount();
        createdAt = board.getCreatedAt();
        modifiedAt = board.getModifiedAt();

        int commentCount = board.getCommentCount();

        BoardDto.Response response = new BoardDto.Response( boardId, title, content, boardStatus, memberId, nickName, viewCount, commentCount, createdAt, modifiedAt );

        return response;
    }
    @Mapping(source = "member.nickName",target = "nickName")
    List<BoardDto.Response> boardsToResponseDtos(List<Board> boards);
    BoardDto.CommentResponseDto commentToBoardResponseDto(Comment comment);

    private long boardMemberMemberId(Board board) {
        if ( board == null ) {
            return 0L;
        }
        Member member = board.getMember();
        if ( member == null ) {
            return 0L;
        }
        long memberId = member.getMemberId();
        return memberId;
    }

    private String boardMemberNickName(Board board) {
        if ( board == null ) {
            return null;
        }
        Member member = board.getMember();
        if ( member == null ) {
            return null;
        }
        String nickName = member.getNickName();
        if ( nickName == null ) {
            return null;
        }
        return nickName;
    }
}

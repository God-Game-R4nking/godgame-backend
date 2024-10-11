package com.example.godgame.comment.mapper;

import com.example.godgame.board.dto.BoardDto;
import com.example.godgame.board.entity.Board;
import com.example.godgame.comment.dto.CommentDto;
import com.example.godgame.comment.entity.Comment;
import com.example.godgame.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "boardId", target = "board.boardId")
    default Comment commentPostDtoToComment(CommentDto.Post postDto) {
        if (postDto == null) {
            return null;
        }

        Comment comment = new Comment();
        Board board = new Board();
        board.setBoardId(postDto.getBoardId());

        Member member = new Member();
        member.setMemberId(postDto.getMemberId());

        comment.setBoard(board);
        comment.setMember(member);

        comment.setCommentContent(postDto.getCommentContent());

        return comment;
    }

    Comment commentPatchDtoToComment(CommentDto.Patch patchDto);

    default CommentDto.Response commentToResponseDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        long commentId = 0L;
        String commentContent = null;
        LocalDateTime createdAt = null;
        LocalDateTime modifiedAt = null;

        commentId = comment.getCommentId();
        commentContent = comment.getCommentContent();
        createdAt = comment.getCreatedAt();
        modifiedAt = comment.getModifiedAt();

        long boardId = comment.getBoard().getBoardId();
        long memberId = comment.getMember().getMemberId();
        String nickName = comment.getMember().getNickName();

        CommentDto.Response response = new CommentDto.Response(commentId, boardId, memberId, commentContent, nickName, createdAt, modifiedAt);

        return response;
    }

    @Mapping(source = "board.boardId", target = "boardId")
    List<CommentDto.Response> commentsToResponseDtos(List<Comment> comments);

    BoardDto.CommentResponseDto commentToBoardResponseDto(Comment comment);
}

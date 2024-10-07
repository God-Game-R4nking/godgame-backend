package com.example.godgame.board.mapper;

import com.example.godgame.board.dto.BoardDto;
import com.example.godgame.board.entity.Board;
import com.example.godgame.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
//    @Mapping(source = "memberId",target = "member.memberId")
    Board boardPostDtoToBoard(BoardDto.Post postDto);
    Board boardPatchDtoToBoard(BoardDto.Patch patchDto);
    @Mapping(source = "member.memberId",target = "memberId")
    BoardDto.Response boardToResponseDto(Board board);
    List<BoardDto.Response> boardsToResponseDtos(List<Board> boards);
    BoardDto.CommentResponseDto commentToBoardResponseDto(Comment comment);
}

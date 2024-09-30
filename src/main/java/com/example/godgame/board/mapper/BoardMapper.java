package com.example.godgame.board.mapper;

import com.example.godgame.board.dto.BoardDto;
import com.example.godgame.board.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
//    @Mapping(source = "member",target = "member.memberId")
    Board boardPostDtoToBoard(BoardDto.Post postDto);
    Board boardPatchDtoToBoard(BoardDto.Patch patchDto);
//    @Mapping(source = "comment.commentContent",target = "commentContent")
//    @Mapping(source = "member.memberId",target = "memberId")
    BoardDto.Response boardToResponseDto(Board board);
    List<BoardDto.Response> boardsToResponseDtos(List<Board> boards);
}

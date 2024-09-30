package com.example.godgame.board.mapper;

import com.example.godgame.board.dto.BoardDto;
import com.example.godgame.board.entity.Board;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board boardPostDtoToBoard(BoardDto.Post postDto);
    Board boardPatchDtoToBoard(BoardDto.Patch patchDto);
    BoardDto.Response boardToResponseDto(Board customer);
    List<BoardDto.Response> boardsToResponseDtos(List<Board> boards);
}

package com.example.godgame.comment.mapper;

import com.example.godgame.comment.dto.CommentDto;
import com.example.godgame.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "boardId",target = "board.boardId")
    Comment commentPostDtoToComment(CommentDto.Post postDto);
    Comment commentPatchDtoToComment(CommentDto.Patch patchDto);
    CommentDto.Response commentToResponseDto(Comment comment);
    List<CommentDto.Response> commentsToResponseDtos(List<Comment> comments);
}

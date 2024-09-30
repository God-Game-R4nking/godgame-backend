package com.example.godgame.comment.controller;

import com.example.godgame.comment.dto.CommentDto;
import com.example.godgame.comment.entity.Comment;
import com.example.godgame.comment.mapper.CommentMapper;
import com.example.godgame.comment.service.CommentService;
import com.example.godgame.dto.SingleResponseDto;
import com.example.godgame.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/comments")
@Validated
@Slf4j
public class CommentController {

    private final static String COMMENT_DEFAULT_URL = "/comments";
    private final CommentService commentService;
    private final CommentMapper commentMapper;


    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping
    public ResponseEntity postComment(@Valid @RequestBody CommentDto.Post requestBody) {
        Comment comment = commentMapper.commentPostDtoToComment(requestBody);
        Comment createdComment = commentService.createComment(comment);
        URI location = UriCreator.createUri(COMMENT_DEFAULT_URL, createdComment.getCommentId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment(@PathVariable("comment-id") @Positive long commentId,
                                       @Valid @RequestBody CommentDto.Patch requestBody){
        requestBody.setCommentId(commentId);

        Comment comment = commentService.updateComment(commentMapper.commentPatchDtoToComment(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(commentMapper.commentToResponseDto(comment)), HttpStatus.OK);
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") @Positive long commentId){

        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

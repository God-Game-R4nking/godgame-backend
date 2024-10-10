package com.example.godgame.comment.controller;

import com.example.godgame.board.entity.Board;
import com.example.godgame.comment.dto.CommentDto;
import com.example.godgame.comment.entity.Comment;
import com.example.godgame.comment.mapper.CommentMapper;
import com.example.godgame.comment.service.CommentService;
import com.example.godgame.dto.MultiResponseDto;
import com.example.godgame.dto.SingleResponseDto;
import com.example.godgame.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

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
    public ResponseEntity postComment(@Valid @RequestBody CommentDto.Post requestBody, Authentication authentication) {
        Comment comment = commentMapper.commentPostDtoToComment(requestBody);
        Comment createdComment = commentService.createComment(comment, authentication);
        URI location = UriCreator.createUri(COMMENT_DEFAULT_URL, createdComment.getCommentId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/gets")
    public ResponseEntity getComments(@Positive @RequestParam int boardId,
            @Positive @RequestParam int page,
                                    @Positive @RequestParam int size){

        Page<Comment> pageComments = commentService.findComments(boardId, page-1, size);
        List<Comment> comments = pageComments.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(commentMapper.commentsToResponseDtos(comments), pageComments), HttpStatus.OK);
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment(@PathVariable("comment-id") @Positive long commentId,
                                       @Valid @RequestBody CommentDto.Patch requestBody,
                                       Authentication authentication){
        requestBody.setCommentId(commentId);

        Comment comment = commentService.updateComment(commentMapper.commentPatchDtoToComment(requestBody), authentication);

        return new ResponseEntity<>(
                new SingleResponseDto<>(commentMapper.commentToResponseDto(comment)), HttpStatus.OK);
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") @Positive long commentId, Authentication authentication){

        commentService.deleteComment(commentId, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

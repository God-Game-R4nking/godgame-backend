package com.example.godgame.comment.service;

import com.example.godgame.board.entity.Board;
import com.example.godgame.board.repository.BoardRepository;
import com.example.godgame.comment.entity.Comment;
import com.example.godgame.comment.repository.CommentRepository;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Comment createComment(Comment comment) {

        Optional<Board> optionalBoard = boardRepository.findById(comment.getBoard().getBoardId());
        Board findBoard = optionalBoard.orElseThrow(()-> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        findBoard.getComments().add(comment);

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Comment comment) {
        Comment findComment = findVerifiedComment(comment.getCommentId());

        Optional.ofNullable(comment.getCommentContent())
                .ifPresent(content -> findComment.setCommentContent(content));

        return commentRepository.save(findComment);
    }

    public void deleteComment(long commentId) {
        Comment findComment = findVerifiedComment(commentId);

        commentRepository.delete(findComment);
    }

    @Transactional(readOnly = true)
    public Comment findVerifiedComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment fineComment = optionalComment.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.COMMENT_EXISTS));
        return fineComment;
    }

    public Board findBoard(long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard = optionalBoard.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findBoard;
    }
}

package com.example.godgame.comment.service;

import com.example.godgame.board.entity.Board;
import com.example.godgame.board.repository.BoardRepository;
import com.example.godgame.comment.entity.Comment;
import com.example.godgame.comment.repository.CommentRepository;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public Comment createComment(Comment comment, Authentication authentication) {
        Optional<Board> optionalBoard = boardRepository.findById(comment.getBoard().getBoardId());
        Board findBoard = optionalBoard.orElseThrow(()-> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        findBoard.getComments().add(comment);
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Comment comment, Authentication authentication) {
        Comment findComment = findVerifiedComment(comment.getCommentId());
        verifiedMemberByComment(comment, authentication);
        if(comment.getCommentStatus().getStatus().equals("삭제 댓글")) {
            throw new BusinessLogicException(ExceptionCode.COMMENT_ALREADY_DELETED);
        }

        Optional.ofNullable(comment.getCommentContent())
                .ifPresent(content -> findComment.setCommentContent(content));
        findComment.setModifiedAt(LocalDateTime.now());

        return commentRepository.save(findComment);
    }

    public Comment findComment(long commentId) {
        Comment comment = findVerifiedComment(commentId);
        if(comment.getCommentStatus().getStatus().equals("삭제 댓글")) {
            throw new BusinessLogicException(ExceptionCode.COMMENT_ALREADY_DELETED);
        }
        return comment;
    }

    public Page<Comment> findComments(long boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("commentId").descending());
        return commentRepository.findByBoardId(boardId, pageable);
    }

    public void deleteComment(long commentId, Authentication authentication) {
        Comment findComment = findVerifiedComment(commentId);
        verifiedMemberByCommentId(commentId, authentication);
        findComment.setModifiedAt(LocalDateTime.now());

        commentRepository.delete(findComment);
    }

    @Transactional(readOnly = true)
    public Comment findVerifiedComment(long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment = optionalComment.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.COMMENT_EXISTS));
        return findComment;
    }

    public boolean verifiedMemberByComment(Comment comment, Authentication authentication) {
        Comment findComment = findComment(comment.getCommentId());
        if(findComment.getMember().getMemberId() == (long) authentication.getPrincipal()) {
            return true;
        } else {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }
    }

    public boolean verifiedMemberByCommentId(long commentId, Authentication authentication) {
        Comment findComment = findComment(commentId);
        if(findComment.getMember().getMemberId() == (long) authentication.getPrincipal()) {
            return true;
        } else {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }
    }

}

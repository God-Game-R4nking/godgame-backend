package com.example.godgame.board.service;

import com.example.godgame.board.dto.BoardDto;
import com.example.godgame.board.entity.Board;
import com.example.godgame.board.mapper.BoardMapper;
import com.example.godgame.board.repository.BoardRepository;
import com.example.godgame.comment.entity.Comment;
import com.example.godgame.comment.mapper.CommentMapper;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public BoardService(BoardRepository boardRepository, BoardMapper boardMapper, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public Board createBoard(Board board, Authentication authentication) {

        verifiedMemberByBoard(board, authentication);
        return boardRepository.save(board);
    }

    public Board updateBoard(Board board, Authentication authentication) {

        Board findBoard = findVerifiedBoard(board.getBoardId());
        verifiedMemberByBoard(board, authentication);
        if(board.getBoardStatus().getStatus().equals("삭제 게시물")) {
            throw new BusinessLogicException(ExceptionCode.BOARD_NOT_VIEWABLE);
        }

        Optional.ofNullable(board.getTitle())
                .ifPresent(title -> findBoard.setTitle(title));
        Optional.ofNullable(board.getContent())
                .ifPresent(content -> findBoard.setContent(content));
        Optional.ofNullable(board.getBoardStatus())
                .ifPresent(boardStatus -> findBoard.setBoardStatus(boardStatus));

        return boardRepository.save(findBoard);
    }



    @Transactional(readOnly = true)
    public Page<Board> findBoards(int page, int size) {
        return boardRepository.findAll(PageRequest.of(page, size, Sort.by("boardId").descending()));
    }

    public void deleteBoard(long boardId, Authentication authentication) {
        Board findBoard = findVerifiedBoard(boardId);
        verifiedMemberByBoardId(boardId, authentication);
        findBoard.setBoardStatus(Board.BoardStatus.BOARD_DELETED);
        boardRepository.save(findBoard);
    }

    @Transactional(readOnly = true)
    public Board findVerifiedBoard(long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard =
                optionalBoard.orElseThrow(()-> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findBoard;
    }

    public boolean verifiedMemberByBoard(Board board, Authentication authentication) {
        Board findBoard = findBoard(board.getBoardId());
        if(findBoard.getMember().getMemberId() == (long) authentication.getPrincipal()) {
            return true;
        } else {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }
    }

    public boolean verifiedMemberByBoardId(long boardId, Authentication authentication) {
        Board findBoard = findBoard(boardId);
        if(findBoard.getMember().getMemberId() == (long) authentication.getPrincipal()) {
            return true;
        } else {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }
    }

    @Transactional(readOnly = true)
    public Board findBoard(long boardId) {

        Board board = findVerifiedBoard(boardId);
        if(board.getBoardStatus().getStatus().equals("삭제 게시물")) {
            throw new BusinessLogicException(ExceptionCode.BOARD_NOT_VIEWABLE);
        }
        return board;
    }

    public BoardDto.Response getBoardWithComments(long boardId, int page, int size) {
        // 게시글 조회
        Board board = findBoard(boardId);

        // 댓글 조회 (페이지네이션 적용)
        Pageable pageable = PageRequest.of(page, size, Sort.by("commentId").descending());
        Page<Comment> comments = commentRepository.findByBoardId(boardId, pageable);

        // 댓글을 DTO 리스트로 변환
        List<BoardDto.CommentResponseDto> commentDtos = comments.getContent().stream()
                .map(commentMapper::commentToBoardResponseDto)
                .collect(Collectors.toList());

        // BoardDto.Response에 댓글 정보 추가
        BoardDto.Response boardResponseDto = boardMapper.boardToResponseDto(board);
        boardResponseDto.setComments(commentDtos);

        boardResponseDto.setTotalPages(comments.getTotalPages()); // 총 페이지 수
        boardResponseDto.setCurrentPage(comments.getNumber()); // 현재 페이지 번호

        return boardResponseDto;
    }
}

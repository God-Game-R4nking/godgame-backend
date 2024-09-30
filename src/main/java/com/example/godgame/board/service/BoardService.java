package com.example.godgame.board.service;

import com.example.godgame.board.entity.Board;
import com.example.godgame.board.repository.BoardRepository;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board createBoard(Board board) {

        return boardRepository.save(board);
    }

    public Board updateBoard(Board board) {

        Board findBoard = findVerifiedBoard(board.getBoardId());

        Optional.ofNullable(board.getTitle())
                .ifPresent(title -> findBoard.setTitle(title));
        Optional.ofNullable(board.getContent())
                .ifPresent(content -> findBoard.setContent(content));
        Optional.ofNullable(board.getBoardStatus())
                .ifPresent(boardStatus -> findBoard.setBoardStatus(boardStatus));

        return boardRepository.save(findBoard);
    }

    @Transactional(readOnly = true)
    public Board findBoard(long boardId) {

        Board board = findVerifiedBoard(boardId);
        if(board.getBoardStatus().getStatus().equals("삭제 게시물")) {
            throw new BusinessLogicException(ExceptionCode.BOARD_NOT_VIEWABLE);
        }
        return board;
    }

    @Transactional(readOnly = true)
    public Page<Board> findBoards(int page, int size) {
        return boardRepository.findAll(PageRequest.of(page, size, Sort.by("boardId").descending()));
    }

    public void deleteBoard(long boardId) {
        Board findBoard = findVerifiedBoard(boardId);
        findBoard.setBoardStatus(Board.BoardStatus.BOARD_DELETED);
        boardRepository.save(findBoard);
    }

    @Transactional
    public Board findVerifiedBoard(long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard =
                optionalBoard.orElseThrow(()-> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findBoard;
    }

    public boolean verifiedMemberId(long boardId, long memberId) {
        Board board = findBoard(boardId);
        if(board.getMember().getMemberId() == memberId) {
            return true;
        } else {
            return false;
        }
    }

}

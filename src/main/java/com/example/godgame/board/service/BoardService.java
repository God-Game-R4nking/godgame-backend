package com.example.godgame.board.service;

import com.example.godgame.board.dto.BoardDto;
import com.example.godgame.board.entity.Board;
import com.example.godgame.board.entity.View;
import com.example.godgame.board.mapper.BoardMapper;
import com.example.godgame.board.repository.BoardRepository;
import com.example.godgame.board.repository.ViewRepository;
import com.example.godgame.comment.entity.Comment;
import com.example.godgame.comment.mapper.CommentMapper;
import com.example.godgame.comment.repository.CommentRepository;
import com.example.godgame.exception.BusinessLogicException;
import com.example.godgame.exception.ExceptionCode;
import com.example.godgame.member.entity.Member;
import com.example.godgame.member.service.MemberService;
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

import static com.example.godgame.board.entity.Board.BoardStatus.BOARD_DELETED;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final MemberService memberService;
    private final ViewRepository viewRepository;

    public BoardService(BoardRepository boardRepository, BoardMapper boardMapper, CommentRepository commentRepository, CommentMapper commentMapper, MemberService memberService, ViewRepository viewRepository) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.memberService = memberService;
        this.viewRepository = viewRepository;
    }

    public Board createBoard(Board board, Authentication authentication) {

        // 유효성 검증
        String id = authentication.getName();
        Member member = memberService.findVerifiedMember(id);
        board.setMember(member);
        String memberId = board.getMember().getId();

        if(memberId == null || memberId.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        if(!memberId.equals((String) authentication.getPrincipal())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }
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

    @Transactional
    public Board findBoard(long boardId, Authentication authentication) {

        Board board = findVerifiedBoard(boardId);
        if(board.getBoardStatus().getStatus().equals("삭제 게시물")) {
            throw new BusinessLogicException(ExceptionCode.BOARD_NOT_VIEWABLE);
        }

        Member member = memberService.findVerifiedMember(authentication.getName());
        if(viewRepository.existsByBoardAndMember(board, member)) {

            return board;
        } else {
            View view = new View();
            view.setBoard(board);
            view.setMember(member);

            System.out.println("Checking views for Board ID: " + boardId + " and Member ID: " + member.getMemberId());

            viewRepository.save(view);

            System.out.println("Saving new view for Board ID: " + boardId + " and Member ID: " + member.getMemberId());

            board.setViewCount(board.getViewCount() + 1);
            boardRepository.save(board);
        }

        return board;
    }

    @Transactional(readOnly = true)
    public Page<Board> findBoards(String title, String content, int page, int size) {
        if (content.isBlank()) {
            return boardRepository.findByTitleContainingAndBoardStatusNot(title, BOARD_DELETED, PageRequest.of(page, size, Sort.by("boardId").descending()));
        } else if (title.isBlank()) {
            return boardRepository.findByContentContainingAndBoardStatusNot(content, BOARD_DELETED, PageRequest.of(page, size, Sort.by("boardId").descending()));
        }
        return boardRepository.findAllByBoardStatusNot(BOARD_DELETED, PageRequest.of(page, size, Sort.by("boardId").descending()));
    }

    @Transactional
    public void deleteBoard(long boardId, Authentication authentication) {
        Board findBoard = findVerifiedBoard(boardId);
        verifiedMemberByBoardId(boardId, authentication);
        findBoard.setBoardStatus(BOARD_DELETED);
        boardRepository.save(findBoard);
    }

    @Transactional(readOnly = true)
    public Board findVerifiedBoard(long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard =
                optionalBoard.orElseThrow(()-> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findBoard;
    }

    public void verifiedMemberByBoard(Board board, Authentication authentication) {
        Board findBoard = findAuthenticateBoard(board.getBoardId(), authentication);
        String findBoardMemberId = String.valueOf(findBoard.getMember().getMemberId());
        String memberId = (String) authentication.getPrincipal();
        if(findBoardMemberId.equals(memberId)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }
    }

    public void verifiedMemberByBoardId(long boardId, Authentication authentication) {
        Board findBoard = findAuthenticateBoard(boardId, authentication);
        String findBoardMemberId = String.valueOf(findBoard.getMember().getId());
        String memberId = (String) authentication.getPrincipal();
        if(!findBoardMemberId.equals(memberId)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_ALLOWED);
        }
    }

    @Transactional(readOnly = true)
    public Board findAuthenticateBoard(long boardId, Authentication authentication) {

        Board board = findVerifiedBoard(boardId);
        if(board.getBoardStatus().getStatus().equals("삭제 게시물")) {
            throw new BusinessLogicException(ExceptionCode.BOARD_NOT_VIEWABLE);
        }

        return board;
    }

    @Transactional
    public Board getBoard(long boardId, Authentication authentication) {
        // 게시글 조회
        return findBoard(boardId, authentication);
    }
}

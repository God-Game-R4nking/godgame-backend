package com.example.godgame.board.controller;

import com.example.godgame.board.dto.BoardDto;
import com.example.godgame.board.entity.Board;
import com.example.godgame.board.mapper.BoardMapper;
import com.example.godgame.board.repository.BoardRepository;
import com.example.godgame.board.service.BoardService;
import com.example.godgame.comment.entity.Comment;
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
@RequestMapping("/boards")
@Validated
@Slf4j
public class BoardController {

    private final static String BOARD_DEFAULT_URL = "/boards";
    private final BoardService boardService;
    private final BoardMapper boardMapper;
    private final CommentService commentService;

    public BoardController(BoardService boardService, BoardMapper boardMapper, CommentService commentService) {
        this.boardService = boardService;
        this.boardMapper = boardMapper;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody BoardDto.Post requestBody, Authentication authentication) {
        Board board = boardMapper.boardPostDtoToBoard(requestBody);
        Board createdBoard = boardService.createBoard(board, authentication);
        URI location = UriCreator.createUri(BOARD_DEFAULT_URL, createdBoard.getBoardId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(@PathVariable("board-id") @Positive long boardId,
                                     @Valid @RequestBody BoardDto.Patch requestBody,
                                     Authentication authentication) {

        requestBody.setBoardId(boardId);

        Board updatedBoard = boardService.updateBoard(boardMapper.boardPatchDtoToBoard(requestBody), authentication);

        return new ResponseEntity<>(new SingleResponseDto<>(boardMapper.boardToResponseDto(updatedBoard)), HttpStatus.OK);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("board-id") @Positive long boardId,
                                   Authentication authentication) {

        // 게시글과 댓글을 함께 조회
        Board board = boardService.getBoard(boardId, authentication);

        return new ResponseEntity(new SingleResponseDto<>(boardMapper.boardToResponseDto(board)), HttpStatus.OK);
    }

    @GetMapping("/gets")
    public ResponseEntity getBoards(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size){

        Page<Board> pageBoards = boardService.findBoards(page-1, size);
        List<Board> boards = pageBoards.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(boardMapper.boardsToResponseDtos(boards), pageBoards), HttpStatus.OK);
    }

    @DeleteMapping("{board-id}")
    public ResponseEntity deleteBoard(@PathVariable("board-id") long boardId, Authentication authentication) {

        boardService.deleteBoard(boardId, authentication);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

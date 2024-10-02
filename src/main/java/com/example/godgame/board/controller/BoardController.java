package com.example.godgame.board.controller;

import com.example.godgame.board.dto.BoardDto;
import com.example.godgame.board.entity.Board;
import com.example.godgame.board.mapper.BoardMapper;
import com.example.godgame.board.repository.BoardRepository;
import com.example.godgame.board.service.BoardService;
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
    private final BoardRepository boardRepository;

    public BoardController(BoardService boardService, BoardMapper boardMapper, BoardRepository boardRepository) {
        this.boardService = boardService;
        this.boardMapper = boardMapper;
        this.boardRepository = boardRepository;
    }

    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody BoardDto.Post requestBody) {

        Board board = boardMapper.boardPostDtoToBoard(requestBody);
        Board createdBoard = boardService.createBoard(board);
        URI location = UriCreator.createUri(BOARD_DEFAULT_URL, createdBoard.getBoardId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(@PathVariable("board-id") @Positive long boardId,
                                     @Valid @RequestBody BoardDto.Patch requestBody) {

        requestBody.setBoardId(boardId);

        Board updatedBoard = boardService.updateBoard(boardMapper.boardPatchDtoToBoard(requestBody));

        return new ResponseEntity<>(new SingleResponseDto<>(boardMapper.boardToResponseDto(updatedBoard)), HttpStatus.OK);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("board-id") @Positive long boardId) {

        Board getBoard = boardService.findBoard(boardId);

        return new ResponseEntity<>(new SingleResponseDto<>(boardMapper.boardToResponseDto(getBoard)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getBoards(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size){

        Page<Board> pageBoards = boardService.findBoards(page-1, size);
        List<Board> boards = pageBoards.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(boardMapper.boardsToResponseDtos(boards), pageBoards), HttpStatus.OK);
    }

    @DeleteMapping("{board-id}")
    public ResponseEntity deleteBoard(@PathVariable("board-id") long boardId) {

        Board board = boardService.findBoard(boardId);

//        if(boardService.verifiedMemberId(boardId, memberId)) {
//           boardService.deleteBoard(boardId);
//        } else {
//            throw new BusinessLogicException(ExceptionCode.BOARD_DELETE_NOT_AVAILABLE);
//        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

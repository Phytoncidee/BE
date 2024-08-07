package com.phytoncide.hikinglog.domain.boards.controller;

import com.phytoncide.hikinglog.base.code.ErrorCode;
import com.phytoncide.hikinglog.base.code.ResponseCode;
import com.phytoncide.hikinglog.base.dto.ResponseDTO;
import com.phytoncide.hikinglog.base.exception.BoardsNotFoundException;
import com.phytoncide.hikinglog.domain.boards.dto.*;
import com.phytoncide.hikinglog.domain.boards.entity.BoardEntity;
import com.phytoncide.hikinglog.domain.boards.service.BoardService;
import com.phytoncide.hikinglog.domain.member.config.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/boards")
@RequiredArgsConstructor
public class BoardsController {
    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<ResponseDTO> boardWrite(@RequestPart("data") BoardWriteDTO boardWriteDTO,
                             @RequestPart(value = "image", required = false) MultipartFile image,
                             @AuthenticationPrincipal AuthDetails authDetails) throws IOException {

        String res = boardService.createBoard(boardWriteDTO, image, authDetails);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_BOARD_WRITE.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_BOARD_WRITE, res));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDTO> boardDelete(@PathVariable("boardId") Integer boardId,
                              @AuthenticationPrincipal AuthDetails authDetails) {

        String res = boardService.deleteBoard(boardId, authDetails);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_BOARD_DELETE.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_BOARD_DELETE, res));
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<ResponseDTO> boardUpdate(@PathVariable("boardId") Integer boardId,
                              @RequestPart("data") BoardWriteDTO boardWriteDTO,
                              @RequestPart(value = "image", required = false) MultipartFile image,
                              @AuthenticationPrincipal AuthDetails authDetails) throws IOException {

        String res = boardService.updateBoard(boardId, boardWriteDTO, image, authDetails);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_BOARD_UPDATE.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_BOARD_UPDATE, res));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDTO> boardRead(@PathVariable("boardId") Integer boardId,
                                                 @AuthenticationPrincipal AuthDetails authDetails) {

        BoardListResponseDTO.BoardResponseDTO res = boardService.readBoard(boardId, authDetails);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_BOARD_READ.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_BOARD_READ, res));
    }

    @GetMapping("")
    public ResponseEntity<ResponseDTO> boardsRead(CursorPageRequestDto cursorPageRequestDto,
                                          @AuthenticationPrincipal AuthDetails authDetails) {
        Long size = cursorPageRequestDto.getSize();
        Integer page = cursorPageRequestDto.getPage();
        List<BoardListResponseDTO.BoardResponseDTO> boardList = boardService.readBoards(size, page, authDetails);

        BoardListResponseDTO res;
        if (!boardService.hasNextBoards(size, page)) {
            res = BoardListResponseDTO.builder()
                    .boardList(boardList)
                    .hasNext(false)
                    .build();
        } else {
            res = BoardListResponseDTO.builder()
                    .boardList(boardList)
                    .hasNext(true)
                    .build();
        }

        return ResponseEntity
                .status(ResponseCode.SUCCESS_BOARD_READ.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_BOARD_READ, res));
    }

    @PostMapping("/{boardId}/comments")
    public ResponseEntity<ResponseDTO> commentWrite(@PathVariable("boardId") Integer boardId,
                               @RequestBody Map<String,String> req,
                               @AuthenticationPrincipal AuthDetails authDetails) {

        String res = boardService.createComment(boardId, req.get("content"), authDetails);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_COMMENT_WRITE.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_COMMENT_WRITE, res));
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<ResponseDTO> commentDelete(@PathVariable("boardId") Integer boardId,
                                                     @PathVariable("commentId") Integer commentId,
                                                     @AuthenticationPrincipal AuthDetails authDetails) {

        String res = boardService.deleteComment(boardId, commentId, authDetails);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_COMMENT_DELETE.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_COMMENT_DELETE, res));
    }

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<ResponseDTO> commentRead(@PathVariable("boardId") Integer boardId,
                                              CursorPageRequestDto cursorPageRequestDto,
                                              @AuthenticationPrincipal AuthDetails authDetails) {
        Long size = cursorPageRequestDto.getSize();
        Integer page = cursorPageRequestDto.getPage();
        List<CommentListResponseDTO.CommentResponseDTO> commentList = boardService.readComments(boardId, size, page, authDetails);

        CommentListResponseDTO res;
        if (!boardService.hasNextComments(boardId, size, page)) {
            res = CommentListResponseDTO.builder()
                    .commentList(commentList)
                    .hasNext(false)
                    .build();
        }

        res = CommentListResponseDTO.builder()
                .commentList(commentList)
                .hasNext(true)
                .build();

        return ResponseEntity
                .status(ResponseCode.SUCCESS_COMMENT_READ.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_COMMENT_READ, res));
    }

    @PostMapping("/{boardId}/like")
    public ResponseEntity<ResponseDTO> boardLike(@PathVariable("boardId") Integer boardId,
                                                 @AuthenticationPrincipal AuthDetails authDetails) throws IOException {

        String res = boardService.createLike(boardId, authDetails);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_BOARD_LIKE.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_BOARD_LIKE, res));
    }

    @DeleteMapping("/{boardId}/like")
    public ResponseEntity<ResponseDTO> boardUnlike(@PathVariable("boardId") Integer boardId,
                                                 @AuthenticationPrincipal AuthDetails authDetails) throws IOException {

        String res = boardService.deleteLike(boardId, authDetails);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_BOARD_UNLIKE.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_BOARD_UNLIKE, res));
    }

    @PostMapping("/notification")
    public ResponseEntity<ResponseDTO> notificationCreate(@RequestBody NotificationCreateDTO notificationCreateDTO,
                                                          @AuthenticationPrincipal AuthDetails authDetails) {
        String res = boardService.createNotification(notificationCreateDTO);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_SAVE_NOTIFICATION.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_SAVE_NOTIFICATION, res));
    }

    @GetMapping("/notification")
    public ResponseEntity<ResponseDTO> notificationRead(CursorPageRequestDto cursorPageRequestDto,
                                                        @AuthenticationPrincipal AuthDetails authDetails) {
        Long size = cursorPageRequestDto.getSize();
        Integer page = cursorPageRequestDto.getPage();
        List<NotificationListResponseDTO.NotificationResponseDTO> notificationList = boardService.readNotifications(size, page, authDetails);

        NotificationListResponseDTO res;
        if (!boardService.hasNextNotifications(authDetails.getMemberEntity(), size, page)) {
            res = NotificationListResponseDTO.builder()
                    .notificationList(notificationList)
                    .hasNext(false)
                    .build();
        }

        res = NotificationListResponseDTO.builder()
                .notificationList(notificationList)
                .hasNext(true)
                .build();

        return ResponseEntity
                .status(ResponseCode.SUCCESS_READ_NOTIFICATION.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_READ_NOTIFICATION, res));
    }

    @DeleteMapping("/notification/{notificationId}")
    public ResponseEntity<ResponseDTO> notificationDelete(@PathVariable("notificationId") Integer notificationId,
                                                          @AuthenticationPrincipal AuthDetails authDetails) {
        String res = boardService.deleteNotification(notificationId, authDetails);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_DELETE_NOTIFICATION.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_DELETE_NOTIFICATION, res));
    }
}

package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.comment.CommentDtoOut;
import by.karpovich.shop.api.dto.comment.CommentForSaveDto;
import by.karpovich.shop.service.client.CommentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping
    public CommentDtoOut save(@RequestHeader(value = "Authorization") String authorization,
                              @Valid @RequestBody CommentForSaveDto dto) {
        return commentService.saveComment(dto, authorization);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserComment(@PathVariable("id") Long commentId,
                                               @RequestHeader(value = "Authorization") String authorization) {

        commentService.deleteUserComment(authorization, commentId);

        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Long commentId,
                                           @Valid @RequestBody CommentForSaveDto dto,
                                           @RequestHeader(value = "Authorization") String authorization) {
        commentService.updateComment(authorization, dto, commentId);

        return new ResponseEntity<>("Comment successfully updated", HttpStatus.OK);
    }
}

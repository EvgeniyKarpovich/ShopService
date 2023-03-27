package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.comment.CommentDtoOut;
import by.karpovich.shop.api.dto.comment.CommentForSaveDto;
import by.karpovich.shop.service.client.CommentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping
    public CommentDtoOut save(@RequestHeader(value = "Authorization") String authorization,
                              @Valid @RequestBody CommentForSaveDto dto) {
        return commentService.save(dto, authorization);
    }
}

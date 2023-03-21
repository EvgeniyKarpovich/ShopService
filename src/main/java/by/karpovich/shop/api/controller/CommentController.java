package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.comment.CommentDtoOut;
import by.karpovich.shop.api.dto.comment.CommentForSaveDto;
import by.karpovich.shop.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentDtoOut save(@Valid @RequestBody CommentForSaveDto dto) {
        return commentService.save(dto);
    }
}

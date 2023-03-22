package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.comment.CommentDtoOut;
import by.karpovich.shop.api.dto.comment.CommentForSaveDto;
import by.karpovich.shop.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentDtoOut save(@Valid @RequestBody CommentForSaveDto dto) {
        return commentService.save(dto);
    }

    @GetMapping("/products/{id}")
    public List<CommentDtoOut> findAllProductCommentsById(@PathVariable("id") Long productId) {
        return commentService.findAllProductCommentsById(productId);
    }
}

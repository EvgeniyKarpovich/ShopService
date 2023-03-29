package by.karpovich.shop.api.controller;

import by.karpovich.shop.service.client.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins/comments")
@RequiredArgsConstructor
public class AdminCommentController {

    private final CommentServiceImpl commentService;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdminCommentById(@PathVariable("id") Long commentId) {
        commentService.deleteAdminComment(commentId);

        return new ResponseEntity<>(String.format("Comment with id = %s successfully deleted", commentId), HttpStatus.OK);
    }
}

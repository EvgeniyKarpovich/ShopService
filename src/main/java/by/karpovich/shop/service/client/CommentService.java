package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.comment.CommentDtoOut;
import by.karpovich.shop.api.dto.comment.CommentForSaveDto;

import java.util.List;

public interface CommentService {

    CommentDtoOut saveComment(CommentForSaveDto dto, String authorization);

    List<CommentDtoOut> findAllProductCommentsByUserId(Long productId);

    void updateComment(String token, CommentForSaveDto dto, Long commentId);

    void deleteUserComment(String token, Long commentId);

    void deleteAdminComment(Long id);
}

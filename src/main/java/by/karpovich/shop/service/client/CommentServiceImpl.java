package by.karpovich.shop.service.client;


import by.karpovich.shop.api.dto.comment.CommentDtoOut;
import by.karpovich.shop.api.dto.comment.CommentForSaveDto;
import by.karpovich.shop.exception.IncorrectUser;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.CommentEntity;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.jpa.repository.CommentRepository;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.mapping.CommentMapper;
import by.karpovich.shop.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserServiceImpl userService;
    private final JwtUtils jwtUtils;
    private final ProductServiceImpl productService;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public CommentDtoOut saveComment(CommentForSaveDto dto, String authorization) {
        String token = authorization.substring(7);
        String userIdFromJWT = jwtUtils.getUserIdFromJWT(token);
        long parseUserId = Long.parseLong(userIdFromJWT);
        ProductEntity product = productService.findProductByIdWhichWillReturnModel(dto.getProductId());

        UserEntity user = userService.findUserByIdWhichWillReturnModel(parseUserId);

        List<ProductEntity> products = user.getProducts().stream()
                .filter(pr -> pr.getId().equals(dto.getProductId()))
                .toList();
        if (products.isEmpty()) {
            throw new IncorrectUser("Users who have not purchased the product cannot comment on this product");
        }
        if (product.getComments().stream()
                .anyMatch(com -> com.getUser().getId().equals(user.getId()))) {
            throw new IncorrectUser("You have already left a review");
        }

        CommentEntity comment = commentMapper.mapEntityFromDto(dto, parseUserId);
        commentRepository.save(comment);
        return commentMapper.mapDtoFromEntity(commentRepository.save(comment));
    }

    @Override
    public List<CommentDtoOut> findAllProductCommentsByUserId(Long productId) {
        if (productRepository.findById(productId).isEmpty()) {
            throw new NotFoundModelException(String.format("Product with id = %s not found", productId));
        } else {
            return commentMapper.mapListDtoFromListEntity(commentRepository.findByProductId(productId));
        }
    }

    @Override
    @Transactional
    public void updateComment(String token, CommentForSaveDto dto, Long commentId) {
        Long userIdFromToken = userService.getUserIdFromToken(token);
        if (userService.findUserByIdWhichWillReturnModel(userIdFromToken).getComments().stream()
                .anyMatch(comment -> comment.getId().equals(commentId))) {
            CommentEntity comment = commentMapper.mapEntityFromDto(dto, userIdFromToken);
            comment.setId(commentId);
            commentRepository.save(comment);
        } else {
            throw new IncorrectUser("You can't delete this comment");
        }
    }

    @Override
    @Transactional
    public void deleteUserComment(String token, Long commentId) {
        Long userIdFromToken = userService.getUserIdFromToken(token);
        if (userService.findUserByIdWhichWillReturnModel(userIdFromToken).getComments().stream()
                .anyMatch(comment -> comment.getId().equals(commentId))) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IncorrectUser("You can't delete this comment");
        }
    }

    @Override
    @Transactional
    public void deleteAdminComment(Long id) {
        if (commentRepository.findById(id).isEmpty()) {
            throw new NotFoundModelException("Comment not found");
        } else {
            commentRepository.deleteById(id);
        }
    }
}

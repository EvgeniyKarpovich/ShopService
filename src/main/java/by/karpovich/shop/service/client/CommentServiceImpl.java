package by.karpovich.shop.service.client;


import by.karpovich.shop.api.dto.comment.CommentDtoOut;
import by.karpovich.shop.api.dto.comment.CommentForSaveDto;
import by.karpovich.shop.exception.IncorrectUser;
import by.karpovich.shop.jpa.entity.CommentEntity;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.repository.CommentRepository;
import by.karpovich.shop.mapping.CommentMapper;
import by.karpovich.shop.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public CommentDtoOut save(CommentForSaveDto dto, String authorization) {
        String token = authorization.substring(7);
        String userIdFromJWT = jwtUtils.getUserIdFromJWT(token);
        long parseUserId = Long.parseLong(userIdFromJWT);
        ProductEntity product = productService.findProductByIdWhichWillReturnModel(dto.getProductId());

        var user = userService.findUserByIdWhichWillReturnModel(parseUserId);

        List<ProductEntity> collect = user.getProducts().stream()
                .filter(pr -> pr.getId().equals(dto.getProductId()))
                .toList();
        if (collect.isEmpty()) {
            throw new IncorrectUser("Users who have not purchased the product cannot comment on this product");
        }
        if (product.getComments().stream()
                .anyMatch(com -> com.getUser().getId().equals(user.getId()))) {
            throw new IncorrectUser("You have already left a review");
        }

        CommentEntity entity = commentMapper.mapEntityFromDto(dto, parseUserId);
        commentRepository.save(entity);
        return commentMapper.mapDtoFromEntity(commentRepository.save(entity));
    }

    @Override
    public List<CommentDtoOut> findAllProductCommentsByUserId(Long productId) {
        return commentMapper.mapListDtoFromListEntity(commentRepository.findByProductId(productId));
    }
}

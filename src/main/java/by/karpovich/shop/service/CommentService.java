package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.comment.CommentDtoOut;
import by.karpovich.shop.api.dto.comment.CommentForSaveDto;
import by.karpovich.shop.exception.IncorrectUser;
import by.karpovich.shop.jpa.entity.CommentEntity;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.repository.CommentRepository;
import by.karpovich.shop.mapping.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @Transactional
    public CommentDtoOut save(CommentForSaveDto dto) {
        var user = userService.findUserByIdWhichWillReturnModel(dto.getUserId());

        List<ProductEntity> collect = user.getProducts().stream()
                .filter(product -> product.getId().equals(dto.getProductId()))
                .toList();
        if (collect.isEmpty()) {
            throw new IncorrectUser("Users who have not purchased the product cannot comment on this product");
        }
        CommentEntity entity = commentMapper.mapEntityFromDto(dto);

        return commentMapper.mapDtoFromEntity(commentRepository.save(entity));
    }

    public List<CommentDtoOut> findAllProductCommentsById(Long userId) {
        return commentMapper.mapListDtoFromListEntity(userService.findUserByIdWhichWillReturnModel(userId).getComments());
    }
}

package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.comment.CommentDtoOut;
import by.karpovich.shop.api.dto.comment.CommentForSaveDto;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.CommentEntity;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.service.UserService;
import by.karpovich.shop.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserService userService;
    private final ProductRepository productRepository;

    public CommentEntity mapEntityFromDto(CommentForSaveDto dto) {
        if (dto == null) {
            return null;
        }

        return CommentEntity.builder()
                .sender(userService.findUserByIdWhichWillReturnModel(dto.getUserId()).getUsername())
                .message(dto.getMessage())
                .rating(dto.getRating())
                .user(userService.findUserByIdWhichWillReturnModel(dto.getUserId()))
                .product(findProductByIdWhichWillReturnModel(dto.getProductId()))
                .build();
    }

    public CommentDtoOut mapDtoFromEntity(CommentEntity entity) {
        if (entity == null) {
            return null;
        }

        return CommentDtoOut.builder()
                .sender(entity.getUser().getUsername())
                .message(entity.getMessage())
                .rating(entity.getRating())
                .date(Utils.mapStringFromInstant(entity.getDateOfCreation()))
                .build();
    }

    public List<CommentDtoOut> mapListDtoFromListEntity(List<CommentEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<CommentDtoOut> dtos = new ArrayList<>();

        for (CommentEntity entity : entities) {
            dtos.add(mapDtoFromEntity(entity));
        }

        return dtos;
    }

    public ProductEntity findProductByIdWhichWillReturnModel(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("Product with id = " + id + "not found"));
    }
}

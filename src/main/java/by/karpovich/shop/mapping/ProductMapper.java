package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.CommentEntity;
import by.karpovich.shop.jpa.entity.OrganizationEntity;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import by.karpovich.shop.service.client.CharacteristicServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CharacteristicMapper characteristicMapper;
    private final CharacteristicServiceImpl characteristicService;
    private final OrganizationRepository organizationRepository;
    private final DiscountMapper discountMapper;
    private final CommentMapper commentMapper;

    public ProductEntity mapEntityFromDto(ProductDtoForSave dto) {
        if (dto == null) {
            return null;
        }

        return ProductEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .organization(findOrgByNameWhichWillReturnModel(dto.getOrganizationName()))
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .keywords(dto.getKeywords())
                .characteristic(characteristicService.findCharacterByIdWhichWillReturnModel(dto.getCharacteristicId()))
                .build();
    }

    public ProductDtoOut mapDtoOutFromEntity(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return ProductDtoOut.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .organizationName(entity.getOrganization().getName())
                .price(entity.getPrice())
                .discount(discountMapper.mapDtoFromEntity(entity.getDiscount()))
                .quantity(entity.getQuantity())
                .averageRating(getSumRatingFromProductComments(entity))
                .keywords(entity.getKeywords())
                .characteristic(characteristicMapper.mapDtoFromEntity(entity.getCharacteristic()))
                .comments(commentMapper.mapListDtoFromListEntity(entity.getComments()))
                .build();
    }

    public List<ProductDtoForFindAll> mapListDtoForFindAllFromListEntity(List<ProductEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<ProductDtoForFindAll> dtoProducts = new ArrayList<>();

        for (ProductEntity entity : entities) {
            dtoProducts.add(ProductDtoForFindAll.builder()
                    .name(entity.getName())
                    .organizationName(entity.getOrganization().getName())
                    .price(entity.getPrice())
                    .build());
        }

        return dtoProducts;
    }

    public String getSumRatingFromProductComments(ProductEntity entity) {
        if (entity.getComments() != null && entity.getComments().size() > 0) {
            Integer sum = entity.getComments().stream()
                    .map(CommentEntity::getRating)
                    .reduce(0, Integer::sum);

            return String.valueOf(sum / entity.getComments().size());
        } else {
            return "No ratings";
        }
    }

    public OrganizationEntity findOrgByNameWhichWillReturnModel(String name) {
        return organizationRepository.findByName(name).orElseThrow(
                () -> new NotFoundModelException("Organization with id = " + name + "not found"));
    }
}


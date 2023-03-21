package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.CharacteristicEntity;
import by.karpovich.shop.jpa.entity.OrganizationEntity;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.repository.CharacteristicRepository;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import ch.qos.logback.core.sift.AppenderFactoryUsingSiftModel;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final OrganizationRepository organizationRepository;
    private final CharacteristicRepository characteristicRepository;
    private final CharacteristicMapper characteristicMapper;

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
                .characteristic(findCharacterByIdWhichWillReturnModel(dto.getCharacteristicId()))
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
                .quantity(entity.getQuantity())
                .keywords(entity.getKeywords())
                .characteristic(characteristicMapper.mapDtoFromEntity(entity.getCharacteristic()))
                .build();
    }

    public List<ProductDtoForFindAll> mapListDtoForFindAllFromListEntity(List<ProductEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<ProductDtoForFindAll> dtos = new ArrayList<>();

        for (ProductEntity entity : entities) {
            ProductDtoForFindAll dto = new ProductDtoForFindAll();
            dto.setName(entity.getName());
            dto.setOrganizationName(entity.getOrganization().getName());
            dto.setPrice(entity.getPrice());
            dto.setQuantity(entity.getQuantity());

            dtos.add(dto);
        }

        return dtos;
    }

    public OrganizationEntity findOrgByNameWhichWillReturnModel(String name) {
        return organizationRepository.findByName(name).orElseThrow(
                () -> new NotFoundModelException("Organization with id = " + name + "not found"));
    }

    public CharacteristicEntity findCharacterByIdWhichWillReturnModel(Long id) {
        return characteristicRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("Characteristic with id = " + id + "not found"));
    }
}

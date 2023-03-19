package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.CharacteristicEntity;
import by.karpovich.shop.jpa.entity.OrganizationEntity;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.repository.CharacteristicRepository;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

        var entity = new ProductEntity();

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setOrganization(findOrgByIdWhichWillReturnModel(dto.getOrganizationId()));
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        entity.setKeywords(dto.getKeywords());
        entity.setCharacteristic(findCharacterByIdWhichWillReturnModel(dto.getCharacteristicId()));

        return entity;
    }

    public ProductDtoOut mapDtoOutFromEntity(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        var dto = new ProductDtoOut();

        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setOrganizationName(entity.getOrganization().getName());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setKeywords(entity.getKeywords());
        dto.setCharacteristic(characteristicMapper.mapDtoFromEntity(entity.getCharacteristic()));

        return dto;
    }

    public OrganizationEntity findOrgByIdWhichWillReturnModel(Long id) {
        var entity = organizationRepository.findById(id);

        return entity.orElseThrow(
                () -> new NotFoundModelException("the organization with id = " + id + "not found"));
    }

    public CharacteristicEntity findCharacterByIdWhichWillReturnModel(Long id) {
        var entity = characteristicRepository.findById(id);

        return entity.orElseThrow(
                () -> new NotFoundModelException("the characteristic with id = " + id + "not found"));
    }
}

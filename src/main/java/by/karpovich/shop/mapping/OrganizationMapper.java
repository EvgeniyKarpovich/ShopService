package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.organization.OrganizationDtoForFindAll;
import by.karpovich.shop.api.dto.organization.OrganizationDtoOut;
import by.karpovich.shop.api.dto.organization.OrganizationForSaveUpdateDto;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.OrganizationEntity;
import by.karpovich.shop.jpa.entity.ShopEntity;
import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.jpa.repository.ShopRepository;
import by.karpovich.shop.jpa.repository.UserRepository;
import by.karpovich.shop.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrganizationMapper {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ProductMapper productMapper;

    public OrganizationEntity mapEntityFromDto(OrganizationForSaveUpdateDto dto, Long userId) {
        if (dto == null) {
            return null;
        }

        return OrganizationEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(StatusOrganization.NOT_VALID)
                .shop(findShopWhichWillReturnModel(dto.getShopId()))
                .user(findUserByIdWhichWillReturnModel(userId))
                .build();
    }

    public OrganizationEntity mapEntityFromDtoForUpdate(OrganizationForSaveUpdateDto dto) {
        if (dto == null) {
            return null;
        }

        return OrganizationEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(StatusOrganization.NOT_VALID)
                .shop(findShopWhichWillReturnModel(dto.getShopId()))
                .build();
    }

    public OrganizationDtoOut mapDtoOutFromEntity(OrganizationEntity entity) {
        if (entity == null) {
            return null;
        }

        return OrganizationDtoOut.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .dateCreation(Utils.mapStringFromInstant(entity.getDateOfCreation()))
                .logo(Utils.getImageAsResponseEntity(entity.getLogo()))
                .products(productMapper.mapListDtoForFindAllFromListEntity(entity.getProducts()))
                .money(entity.getMoney())
                .build();
    }

    public List<OrganizationDtoForFindAll> mapListDtoForFindAllFromListEntity(List<OrganizationEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<OrganizationDtoForFindAll> dtos = new ArrayList<>();

        for (OrganizationEntity entity : entities) {
            OrganizationDtoForFindAll dto = new OrganizationDtoForFindAll();
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setDateCreation(Utils.mapStringFromInstant(entity.getDateOfCreation()));

            dtos.add(dto);
        }

        return dtos;
    }

    public UserEntity findUserByIdWhichWillReturnModel(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("User with id = " + id + "not found"));
    }

    public ShopEntity findShopWhichWillReturnModel(Long id) {
        return shopRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("Shop not found"));
    }
}

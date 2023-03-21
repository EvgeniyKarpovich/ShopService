package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.organization.OrganizationDtoForFindAll;
import by.karpovich.shop.api.dto.organization.OrganizationDtoOut;
import by.karpovich.shop.api.dto.organization.OrganizationForSaveUpdateDto;
import by.karpovich.shop.jpa.entity.OrganizationEntity;
import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrganizationMapper {

    public OrganizationEntity mapEntityFromDto(OrganizationForSaveUpdateDto dto) {
        if (dto == null) {
            return null;
        }

        return OrganizationEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(StatusOrganization.ACTIVE)
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
}

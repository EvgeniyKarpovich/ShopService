package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.organization.OrganizationForSaveDto;
import by.karpovich.shop.jpa.entity.OrganizationEntity;
import by.karpovich.shop.jpa.entity.StatusOrganization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public OrganizationEntity mapEntityFromDto(OrganizationForSaveDto dto) {
        if (dto == null) {
            return null;
        }

        return OrganizationEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(StatusOrganization.ACTIVE)
                .build();
    }
}

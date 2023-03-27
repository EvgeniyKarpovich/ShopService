package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.organization.OrganizationDtoForFindAll;
import by.karpovich.shop.api.dto.organization.OrganizationDtoOut;
import by.karpovich.shop.api.dto.organization.OrganizationForSaveUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrganizationService {

    void saveOrganization(OrganizationForSaveUpdateDto dto, String authorization);

    OrganizationDtoOut findOrganizationById(Long id);

    List<OrganizationDtoForFindAll> findAllOrganizations();

    OrganizationDtoOut updateOrganizationById(OrganizationForSaveUpdateDto dto, Long id);

    void deleteOrganizationById(Long id);

    void addLogoForOrganization(Long organizationId, MultipartFile file);
}

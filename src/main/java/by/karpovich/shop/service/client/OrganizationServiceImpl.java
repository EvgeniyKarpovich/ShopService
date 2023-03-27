package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.organization.OrganizationDtoForFindAll;
import by.karpovich.shop.api.dto.organization.OrganizationDtoOut;
import by.karpovich.shop.api.dto.organization.OrganizationForSaveUpdateDto;
import by.karpovich.shop.exception.DuplicateException;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.OrganizationEntity;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import by.karpovich.shop.mapping.OrganizationMapper;
import by.karpovich.shop.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final UserServiceImpl userService;

    @Transactional
    public void saveOrganization(OrganizationForSaveUpdateDto dto, String authorization) {
        var userIdFromToken = userService.getUserIdFromToken(authorization);

        checkOrganizationForDuplicate(dto, null);
        var organization = organizationMapper.mapEntityFromDto(dto, userIdFromToken);

        log.info("method save - Organization with name {} saved", organization.getName());
        organizationRepository.save(organization);
    }

    public OrganizationDtoOut findOrganizationById(Long id) {
        var entity = organizationRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("not found"));

        log.info("method findById - Organization found with id = {} ", entity.getId());
        return organizationMapper.mapDtoOutFromEntity(entity);
    }

    public List<OrganizationDtoForFindAll> findAllOrganizations() {
        var organizationEntities = organizationRepository.findAll();

        log.info("method findAll - organizations found  = {} ", organizationEntities.size());
        return organizationMapper.mapListDtoForFindAllFromListEntity(organizationEntities);
    }

    @Transactional
    public OrganizationDtoOut updateOrganizationById(OrganizationForSaveUpdateDto dto, Long id) {
        checkOrganizationForDuplicate(dto, id);

        var entity = organizationMapper.mapEntityFromDtoForUpdate(dto);
        entity.setId(id);
        var updatedEntity = organizationRepository.save(entity);

        log.info("method update - organization {} updated", updatedEntity.getName());
        return organizationMapper.mapDtoOutFromEntity(updatedEntity);
    }

    @Transactional
    public void deleteOrganizationById(Long id) {
        if (organizationRepository.findById(id).isPresent()) {
            organizationRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format("organization with id = %s not found", id));
        }
        log.info("method deleteById - organization with id = {} deleted", id);
    }

    @Transactional
    public void addLogoForOrganization(Long organizationId, MultipartFile file) {
        var entity = organizationRepository.findById(organizationId)
                .orElseThrow(
                        () -> new NotFoundModelException("not found"));

        entity.setLogo(Utils.saveFile(file));
        organizationRepository.save(entity);
    }

    private void checkOrganizationForDuplicate(OrganizationForSaveUpdateDto dto, Long id) {
        Optional<OrganizationEntity> entity = organizationRepository.findByName(dto.getName());

        if (entity.isPresent() && !entity.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Organization with name = %s already exist", dto.getName()));
        }
    }
}

package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.organization.OrganizationDtoForFindAll;
import by.karpovich.shop.api.dto.organization.OrganizationDtoOut;
import by.karpovich.shop.api.dto.organization.OrganizationForSaveUpdateDto;
import by.karpovich.shop.exception.DuplicateException;
import by.karpovich.shop.exception.IncorrectUser;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.OrganizationEntity;
import by.karpovich.shop.jpa.entity.UserEntity;
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

    @Override
    @Transactional
    public void saveOrganization(OrganizationForSaveUpdateDto dto, String authorization) {
        Long userIdFromToken = userService.getUserIdFromToken(authorization);

        checkOrganizationForDuplicate(dto, null);
        OrganizationEntity organization = organizationMapper.mapEntityFromDto(dto, userIdFromToken);

        log.info("method save - Organization with name {} saved", organization.getName());
        organizationRepository.save(organization);
    }

    @Override
    public OrganizationDtoOut findOrganizationById(Long organizationId) {
        OrganizationEntity organization = organizationRepository.findById(organizationId).orElseThrow(
                () -> new NotFoundModelException(String.format("Organization with id = %s not found", organizationId)));

        log.info("method findById - Organization found with id = {} ", organization.getId());
        return organizationMapper.mapDtoOutFromEntity(organization);
    }

    @Override
    public List<OrganizationDtoForFindAll> findAllOrganizations() {
        List<OrganizationEntity> organizations = organizationRepository.findAll();

        log.info("method findAll - organizations found  = {} ", organizations.size());
        return organizationMapper.mapListDtoForFindAllFromListEntity(organizations);
    }

    @Override
    @Transactional
    public OrganizationDtoOut updateOrganizationById(OrganizationForSaveUpdateDto orgDto, Long orgId, String token) {
        checkOrganizationForDuplicate(orgDto, orgId);
        UserEntity user = userService.findUserEntityByIdFromToken(token);
        OrganizationEntity organizationUpdated;

        if (user.getOrganizations().stream()
                .anyMatch(org -> org.getId().equals(orgId))) {

            OrganizationEntity organizationEntity = organizationMapper.mapEntityFromDtoForUpdate(orgDto);
            organizationEntity.setId(orgId);
            organizationUpdated = organizationRepository.save(organizationEntity);
        } else {
            throw new IncorrectUser("You can't update this organization");
        }
        log.info("method update - organization {} updated", organizationUpdated.getName());
        return organizationMapper.mapDtoOutFromEntity(organizationUpdated);
    }

    @Override
    @Transactional
    public void deleteOrganizationById(Long organizationId, String token) {
        UserEntity user = userService.findUserEntityByIdFromToken(token);
        if (user.getOrganizations().stream()
                .anyMatch(org -> org.getId().equals(organizationId))) {
            throw new IncorrectUser("You can't delete this organization");
        }
        if (organizationRepository.findById(organizationId).isPresent()) {
            organizationRepository.deleteById(organizationId);
        } else {
            throw new NotFoundModelException(String.format("organization with id = %s not found", organizationId));
        }
        log.info("method deleteById - organization with id = {} deleted", organizationId);
    }

    @Override
    @Transactional
    public void addLogoForOrganization(Long organizationId, MultipartFile file, String token) {
        UserEntity user = userService.findUserEntityByIdFromToken(token);
        if (user.getOrganizations().stream()
                .anyMatch(org -> org.getId().equals(organizationId))) {
            throw new IncorrectUser("You can't set logo this organization");
        }

        OrganizationEntity organization = organizationRepository.findById(organizationId)
                .orElseThrow(
                        () -> new NotFoundModelException(String.format("Organization with id = %s not found", organizationId)));

        organization.setLogo(Utils.saveFile(file));
        organizationRepository.save(organization);
    }

    private void checkOrganizationForDuplicate(OrganizationForSaveUpdateDto dto, Long id) {
        Optional<OrganizationEntity> entity = organizationRepository.findByName(dto.getName());

        if (entity.isPresent() && !entity.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Organization with name = %s already exist", dto.getName()));
        }
    }
}

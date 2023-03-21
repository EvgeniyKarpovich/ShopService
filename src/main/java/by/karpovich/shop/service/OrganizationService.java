package by.karpovich.shop.service;

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
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    @Transactional
    public void save(OrganizationForSaveUpdateDto dto) {
        validateAlreadyExists(dto, null);
        var entity = organizationMapper.mapEntityFromDto(dto);

        log.info("method save - Organization with name {} saved", entity.getName());
        organizationRepository.save(entity);
    }

    public OrganizationDtoOut findById(Long id) {
        var entity = organizationRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("not found"));

        log.info("method findById - Organization found with id = {} ", entity.getId());
        return organizationMapper.mapDtoOutFromEntity(entity);
    }

    public List<OrganizationDtoForFindAll> findAll() {
        var entities = organizationRepository.findAll();

        log.info("method findAll - organizations found  = {} ", entities.size());
        return organizationMapper.mapListDtoForFindAllFromListEntity(entities);
    }

    @Transactional
    public OrganizationDtoOut update(OrganizationForSaveUpdateDto dto, Long id) {
        validateAlreadyExists(dto, id);

        var entity = organizationMapper.mapEntityFromDto(dto);
        entity.setId(id);
        var updatedEntity = organizationRepository.save(entity);

        log.info("method update - organization {} updated", updatedEntity.getName());
        return organizationMapper.mapDtoOutFromEntity(updatedEntity);
    }

    @Transactional
    public void deleteById(Long id) {
        if (organizationRepository.findById(id).isPresent()) {
            organizationRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format("organization with id = %s not found", id));
        }
        log.info("method deleteById - organization with id = {} deleted", id);
    }

    public void addLogo(Long organizationId, MultipartFile file) {
        var entity = organizationRepository.findById(organizationId)
                .orElseThrow(
                        () -> new NotFoundModelException("not found"));

        entity.setLogo(Utils.saveFile(file));
    }

    private void validateAlreadyExists(OrganizationForSaveUpdateDto dto, Long id) {
        Optional<OrganizationEntity> entity = organizationRepository.findByName(dto.getName());

        if (entity.isPresent() && !entity.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Organization with name = %s already exist", dto.getName()));
        }
    }
}

package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.organization.OrganizationForSaveDto;
import by.karpovich.shop.exception.DuplicateException;
import by.karpovich.shop.jpa.entity.OrganizationEntity;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import by.karpovich.shop.mapping.OrganizationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    public void save(OrganizationForSaveDto dto) {
        validateAlreadyExists(dto, null);
        var entity = organizationMapper.mapEntityFromDto(dto);

        organizationRepository.save(entity);
    }


    private void validateAlreadyExists(OrganizationForSaveDto dto, Long id) {
        Optional<OrganizationEntity> entity = organizationRepository.findByName(dto.getName());

        if (entity.isPresent() && !entity.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Organization with name = %s already exist", dto.getName()));
        }
    }
}

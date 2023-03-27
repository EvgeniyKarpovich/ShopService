package by.karpovich.shop.service.admin;

import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminOrganizationServiceImpl implements AdminOrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public void setOrganizationStatus(Long organizationId, StatusOrganization status) {
        if (organizationRepository.findById(organizationId).isPresent()) {
            organizationRepository.setStatus(organizationId, status);
        } else {
            throw new NotFoundModelException(String.format("Organization with id = %s not found", organizationId));
        }
    }
}

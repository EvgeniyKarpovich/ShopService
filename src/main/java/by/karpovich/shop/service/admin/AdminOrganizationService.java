package by.karpovich.shop.service.admin;

import by.karpovich.shop.jpa.entity.StatusOrganization;

public interface AdminOrganizationService {

    void setOrganizationStatus(Long organizationId, StatusOrganization status);
}

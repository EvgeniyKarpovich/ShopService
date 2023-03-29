package by.karpovich.shop.api.controller;

import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.service.admin.AdminOrganizationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins/organizations")
@RequiredArgsConstructor
public class AdminOrganizationsController {

    private final AdminOrganizationServiceImpl adminOrganizationService;

    @PutMapping("/statuses/frozen/{id}/{status}")
    public void setFrozenStatusOrg(@PathVariable("id") Long id, @PathVariable("status") StatusOrganization status) {
        adminOrganizationService.setOrganizationStatus(id, StatusOrganization.FROZEN);
    }

    @PutMapping("/statuses/deleted/{id}/{status}")
    public void setDeletedStatusOrg(@PathVariable("id") Long id, @PathVariable("status") StatusOrganization status) {
        adminOrganizationService.setOrganizationStatus(id, StatusOrganization.DELETED);
    }

    @PutMapping("/statuses/active/{id}/{status}")
    public void setActiveStatusOrg(@PathVariable("id") Long id, @PathVariable("status") StatusOrganization status) {
        adminOrganizationService.setOrganizationStatus(id, StatusOrganization.ACTIVE);
    }
}

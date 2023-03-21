package by.karpovich.shop.api.controller;

import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final ProductRepository productRepository;

    @PutMapping("/valid/{id}")
    public void doProductValid(@PathVariable("id") Long productId) {
        productRepository.doValidProduct(productId);
    }

    @PutMapping("/balance/inc/{id}/{sum}")
    public void addBalance(@PathVariable("id") Long userId, @PathVariable("sum") Double sum) {
        userRepository.addBalance(userId, sum);
    }

    @PutMapping("/statusFrozen/{id}/{status}")
    public void setFrozenStatus(@PathVariable("id") Long id, @PathVariable("status") StatusOrganization status) {
        organizationRepository.setStatus(id, StatusOrganization.FROZEN);
    }

    @PutMapping("/statusDeleted/{id}/{status}")
    public void setDeletedStatus(@PathVariable("id") Long id, @PathVariable("status") StatusOrganization status) {
        organizationRepository.setStatus(id, StatusOrganization.DELETED);
    }

    @PutMapping("/statusActive{id}/{status}")
    public void setActiveStatus(@PathVariable("id") Long id, @PathVariable("status") StatusOrganization status) {
        organizationRepository.setStatus(id, StatusOrganization.ACTIVE);
    }

}

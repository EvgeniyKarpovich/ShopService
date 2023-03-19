package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.organization.OrganizationForSaveDto;
import by.karpovich.shop.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    private void save(@RequestBody OrganizationForSaveDto dto) {
        organizationService.save(dto);
    }
}

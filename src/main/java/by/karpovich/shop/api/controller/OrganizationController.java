package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.organization.OrganizationDtoOut;
import by.karpovich.shop.api.dto.organization.OrganizationForSaveUpdateDto;
import by.karpovich.shop.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    private void save(@RequestBody OrganizationForSaveUpdateDto dto) {
        organizationService.save(dto);
    }

    @PutMapping("/images/{id}")
    public void addLogo(@PathVariable("id") Long organizationId, @RequestPart("file") MultipartFile file) {
        organizationService.addLogo(organizationId, file);
    }

    @GetMapping("/{id}")
    public OrganizationDtoOut findById(@PathVariable("id") Long id) {
        return organizationService.findById(id);
    }
}

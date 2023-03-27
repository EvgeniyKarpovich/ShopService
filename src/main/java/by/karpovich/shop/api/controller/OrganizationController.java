package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.organization.OrganizationDtoOut;
import by.karpovich.shop.api.dto.organization.OrganizationForSaveUpdateDto;
import by.karpovich.shop.service.client.OrganizationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationServiceImpl organizationService;

    @PostMapping
    private ResponseEntity<?> save(@RequestHeader(value = "Authorization") String authorization,
                                   @Valid @RequestBody OrganizationForSaveUpdateDto dto) {
        organizationService.saveOrganization(dto, authorization);

        return new ResponseEntity<>("Organization saved successfully", HttpStatus.OK);
    }

    @PutMapping("/images/{id}")
    public void addLogo(@PathVariable("id") Long organizationId, @RequestPart("file") MultipartFile file) {
        organizationService.addLogoForOrganization(organizationId, file);
    }

    @GetMapping("/{id}")
    public OrganizationDtoOut findById(@PathVariable("id") Long orgId) {
        return organizationService.findOrganizationById(orgId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long orgId) {
        organizationService.deleteOrganizationById(orgId);

        return new ResponseEntity<>("Organization deleted", HttpStatus.OK);
    }
}

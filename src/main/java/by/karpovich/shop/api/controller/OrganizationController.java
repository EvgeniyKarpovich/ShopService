package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.organization.OrganizationDtoForFindAll;
import by.karpovich.shop.api.dto.organization.OrganizationDtoOut;
import by.karpovich.shop.api.dto.organization.OrganizationForSaveUpdateDto;
import by.karpovich.shop.service.client.OrganizationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public void addLogo(@RequestHeader(value = "Authorization") String authorization,
                        @PathVariable("id") Long organizationId,
                        @RequestPart("file") MultipartFile file) {
        organizationService.addLogoForOrganization(organizationId, file, authorization);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateOrganizationById(@PathVariable("id") Long orgId,
                                                     @RequestHeader(value = "Authorization") String authorization,
                                                     @Valid @RequestBody OrganizationForSaveUpdateDto dto) {
        organizationService.saveOrganization(dto, authorization);

        return new ResponseEntity<>("Organization updated successfully", HttpStatus.OK);
    }

    @GetMapping
    public List<OrganizationDtoForFindAll> findAllOrganization() {
        return organizationService.findAllOrganizations();
    }

    @GetMapping("/{id}")
    public OrganizationDtoOut findById(@PathVariable("id") Long orgId) {
        return organizationService.findOrganizationById(orgId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@RequestHeader(value = "Authorization") String authorization,
                                        @PathVariable("id") Long orgId) {
        organizationService.deleteOrganizationById(orgId, authorization);

        return new ResponseEntity<>("Organization deleted", HttpStatus.OK);
    }
}

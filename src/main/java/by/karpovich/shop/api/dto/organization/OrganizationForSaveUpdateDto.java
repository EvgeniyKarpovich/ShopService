package by.karpovich.shop.api.dto.organization;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationForSaveUpdateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}

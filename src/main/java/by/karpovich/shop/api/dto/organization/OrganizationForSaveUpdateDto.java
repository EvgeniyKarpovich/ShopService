package by.karpovich.shop.api.dto.organization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationForSaveUpdateDto {

    @NotBlank(message = "Enter name")
    private String name;

    @NotBlank(message = "Enter description")
    private String description;

    @NotNull(message = "Enter shopId")
    private Long shopId;
}

package by.karpovich.shop.api.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDtoForFindAll {

    private String name;

    private String description;

    private String dateCreation;
}

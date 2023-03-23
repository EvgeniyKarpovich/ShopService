package by.karpovich.shop.api.dto.organization;


import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDtoOut {

    private String name;

    private String description;

    private String dateCreation;

    private byte[] logo;

    private Double money;

    private List<ProductDtoForFindAll> products;
}

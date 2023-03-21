package by.karpovich.shop.api.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoForFindAll {

    private String name;

    private String organizationName;

    private Double price;

    private Integer quantity;
}

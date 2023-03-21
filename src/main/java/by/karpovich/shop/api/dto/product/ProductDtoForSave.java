package by.karpovich.shop.api.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoForSave {

    private String name;

    private String description;

    private String organizationName;

    private Double price;

    private Integer quantity;

    private List<String> keywords = new ArrayList<>();

    private Long characteristicId;
}

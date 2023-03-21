package by.karpovich.shop.api.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String organizationName;

    @Positive
    private Double price;

    @Positive
    private Integer quantity;

    private List<String> keywords = new ArrayList<>();

    private Long characteristicId;
}

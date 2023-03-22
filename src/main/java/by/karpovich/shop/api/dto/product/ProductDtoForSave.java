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

    @NotBlank(message = "Enter name")
    private String name;

    private String description;

    @NotBlank(message = "Enter name organization")
    private String organizationName;

    @Positive(message = "Price must be more 0")
    private Double price;

    @Positive(message = "Quantity must be more 0")
    private Integer quantity;

    private List<String> keywords = new ArrayList<>();

    private Long characteristicId;
}

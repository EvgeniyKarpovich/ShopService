package by.karpovich.shop.api.dto.discount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {

    @NotBlank(message = "Enter name")
    private String name;

    @NotNull(message = "Enter discount percentage")
    private int discountPercentage;

    @NotNull(message = "start discount")
    private Instant startDiscount;

    @NotNull(message = "finish discount")
    private Instant finishDiscount;
}

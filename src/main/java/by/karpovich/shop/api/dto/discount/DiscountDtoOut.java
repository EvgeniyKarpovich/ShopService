package by.karpovich.shop.api.dto.discount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDtoOut {

    private String name;

    private int discountPercentage;

    private String startDiscount;

    private String finishDiscount;
}

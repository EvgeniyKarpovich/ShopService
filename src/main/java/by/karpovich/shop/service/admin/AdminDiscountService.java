package by.karpovich.shop.service.admin;

import by.karpovich.shop.api.dto.discount.DiscountDto;
import by.karpovich.shop.api.dto.discount.DiscountDtoOut;

import java.util.List;

public interface AdminDiscountService {

    void saveDiscount(DiscountDto dto);

    DiscountDtoOut findDiscountById(Long id);

    List<DiscountDtoOut> findAllDiscounts();

    void deleteDiscountById(Long id);

    DiscountDtoOut updateDiscountById(DiscountDto dto, Long id);

    void addDiscount(List<Long> productsId, Long discountId);

    void deleteDiscountFromProducts(List<Long> productsId, Long discountId);
}

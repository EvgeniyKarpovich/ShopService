package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.discount.DiscountDto;
import by.karpovich.shop.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    private void save(@RequestBody DiscountDto dto) {
        discountService.save(dto);
    }
}

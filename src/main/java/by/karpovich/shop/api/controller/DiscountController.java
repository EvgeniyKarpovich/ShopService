package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.discount.DiscountDto;
import by.karpovich.shop.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    private ResponseEntity<?> save(@Valid @RequestBody DiscountDto dto) {
        discountService.save(dto);

        return new ResponseEntity<>("Discount saved successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public void updateDiscount(@PathVariable("id") Long id, @Valid @RequestBody DiscountDto dto) {
        discountService.update(dto, id);
    }
}

package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.discount.DiscountDto;
import by.karpovich.shop.service.admin.AdminDiscountServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminDiscountController {

    private final AdminDiscountServiceImpl adminDiscountService;

    @PutMapping("/discounts/{disId}/")
    public void addDiscount(@PathVariable("disId") Long disId, @RequestParam("productsId") List<Long> productsId) {
        adminDiscountService.addDiscount(productsId, disId);
    }

    @PutMapping("/discounts/remove/{discountId}")
    public void deleteDiscountFromProducts(@PathVariable("discountId") Long discountId,
                                           @RequestParam("productsId") List<Long> productsId) {
        adminDiscountService.deleteDiscountFromProducts(productsId, discountId);
    }

    @PostMapping
    private ResponseEntity<?> save(@Valid @RequestBody DiscountDto dto) {
        adminDiscountService.saveDiscount(dto);

        return new ResponseEntity<>("Discount saved successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public void updateDiscount(@PathVariable("id") Long id, @Valid @RequestBody DiscountDto dto) {
        adminDiscountService.updateDiscountById(dto, id);
    }
}

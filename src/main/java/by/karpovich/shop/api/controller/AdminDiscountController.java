package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.discount.DiscountDto;
import by.karpovich.shop.api.dto.discount.DiscountDtoOut;
import by.karpovich.shop.service.admin.AdminDiscountServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins/discounts")
@RequiredArgsConstructor
public class AdminDiscountController {

    private final AdminDiscountServiceImpl adminDiscountService;

    @PutMapping("/{disId}/")
    public void addDiscount(@PathVariable("disId") Long disId, @RequestParam("productsId") List<Long> productsId) {
        adminDiscountService.addDiscount(productsId, disId);
    }

    @PutMapping("/remove/{discountId}")
    public void deleteDiscountFromProducts(@PathVariable("discountId") Long discountId,
                                           @RequestParam("productsId") List<Long> productsId) {
        adminDiscountService.deleteDiscountFromProducts(productsId, discountId);
    }

    @PostMapping
    private ResponseEntity<?> saveDiscount(@Valid @RequestBody DiscountDto dto) {
        adminDiscountService.saveDiscount(dto);

        return new ResponseEntity<>("Discount saved successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public void updateDiscountById(@PathVariable("id") Long id, @Valid @RequestBody DiscountDto dto) {
        adminDiscountService.updateDiscountById(dto, id);
    }

    @GetMapping
    public List<DiscountDtoOut> findAllDiscounts() {
        return adminDiscountService.findAllDiscounts();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiscountById(@PathVariable("id") Long discountId) {
        adminDiscountService.deleteDiscountById(discountId);

        return new ResponseEntity<>("Discount deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public DiscountDtoOut findDiscountById(@PathVariable("/id") Long discountId) {
        return adminDiscountService.findDiscountById(discountId);
    }
}


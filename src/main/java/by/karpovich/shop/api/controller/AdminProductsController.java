package by.karpovich.shop.api.controller;

import by.karpovich.shop.service.admin.AdminProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins/products")
@RequiredArgsConstructor
public class AdminProductsController {

    private final AdminProductServiceImpl adminProductService;

    @PutMapping("/valid/{id}")
    public ResponseEntity<?> doProductValid(@PathVariable("id") Long productId) {
        adminProductService.doValidProduct(productId);

        return new ResponseEntity<>("Product is valid", HttpStatus.OK);
    }
}

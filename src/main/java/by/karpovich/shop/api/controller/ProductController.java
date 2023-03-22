package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping
    public void save(@Valid @RequestBody ProductDtoForSave dto) {
        productService.save(dto);
    }

    @GetMapping("/{id}")
    public ProductDtoOut findById(@PathVariable("id") Long id) {

        return productService.findById(id);
    }

    @PutMapping("/buy/{userId}/{productId}")
    public void buy(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) {
        productService.buyProduct(userId, productId);
    }

    @PutMapping("/discounts/{id}/{disId}")
    public void addDiscount(@PathVariable("id") Long productId, @PathVariable("disId") Long disId) {
        productService.addDiscount(productId, disId);
    }

    @GetMapping
    public List<ProductDtoForFindAll> findAll() {
        return productService.findAll();
    }

    @PutMapping("/{id}")
    public void decQua(@PathVariable("id") Long productId) {
        productRepository.decrementQuantity(productId);
    }
}

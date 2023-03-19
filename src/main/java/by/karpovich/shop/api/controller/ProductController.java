package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public void save(@RequestBody ProductDtoForSave dto) {
        productService.save(dto);
    }

    @GetMapping("/{id}")
    public ProductDtoOut findById(@PathVariable("id") Long id) {

        return productService.findById(id);
    }
}

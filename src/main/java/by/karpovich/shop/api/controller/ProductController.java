package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.security.JwtUtils;
import by.karpovich.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final JwtUtils jwtUtils;

    @PostMapping
    public void save(@Valid @RequestBody ProductDtoForSave dto) {
        productService.save(dto);
    }

    @GetMapping("/{id}")
    public ProductDtoOut findById(@PathVariable("id") Long id) {

        return productService.findById(id);
    }

    @PutMapping("/returns/{productId}")
    public void returnProduct(@RequestHeader(value = "Authorization") String authorization,
                              @PathVariable("productId") Long productId) {
        String token = authorization.substring(7);
        String userIdFromJWT = jwtUtils.getUserIdFromJWT(token);

        productService.returnProduct(Long.valueOf(userIdFromJWT), productId);
    }

    @PutMapping("/buy/{productId}")
    public void buy(@RequestHeader(value = "Authorization") String authorization,
                    @PathVariable("productId") Long productId) {
        String token = authorization.substring(7);
        String userIdFromJWT = jwtUtils.getUserIdFromJWT(token);

        productService.buyProduct(Long.valueOf(userIdFromJWT), productId);
    }

    @PutMapping("/discounts/{disId}/")
    public void addDiscount(@PathVariable("disId") Long disId, @RequestParam("productsId") List<Long> productsId) {
        productService.addDiscount(productsId, disId);
    }

    @PutMapping("/discounts/remove/{discountId}")
    public void deleteDiscountFromProducts(@PathVariable("discountId") Long discountId,
                                           @RequestParam("productsId") List<Long> productsId) {
        productService.deleteDiscountFromProducts(productsId, discountId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long productId) {
        productService.deleteById(productId);

        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }

    @GetMapping
    public List<ProductDtoForFindAll> findAll() {
        return productService.findAll();
    }
}

package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.comment.CommentDtoOut;
import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.service.client.CommentServiceImpl;
import by.karpovich.shop.service.client.ProductServiceImpl;
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

    private final CommentServiceImpl commentService;
    private final ProductServiceImpl productService;

    @PostMapping
    public void save(@Valid @RequestBody ProductDtoForSave dto) {
        productService.saveProduct(dto);
    }

    @GetMapping("/{id}")
    public ProductDtoOut findById(@PathVariable("id") Long id) {

        return productService.findProductById(id);
    }

    @GetMapping("/products/{id}")
    public List<CommentDtoOut> findAllProductCommentsById(@PathVariable("id") Long productId) {
        return commentService.findAllProductCommentsByUserId(productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long productId) {
        productService.deleteProductById(productId);

        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }

    @GetMapping
    public List<ProductDtoForFindAll> findAll() {
        return productService.findAllValidProducts();
    }
}

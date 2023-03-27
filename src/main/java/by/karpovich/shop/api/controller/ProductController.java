package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.comment.CommentDtoOut;
import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.service.client.CommentServiceImpl;
import by.karpovich.shop.service.client.ProductServiceImpl;
import by.karpovich.shop.service.client.UserServiceImpl;
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
    private final UserServiceImpl userService;

    @PostMapping
    public void save(@Valid @RequestBody ProductDtoForSave dto) {
        productService.save(dto);
    }

    @GetMapping("/{id}")
    public ProductDtoOut findById(@PathVariable("id") Long id) {

        return productService.findById(id);
    }

    @GetMapping("/products/{id}")
    public List<CommentDtoOut> findAllProductCommentsById(@PathVariable("id") Long productId) {
        return commentService.findAllProductCommentsByUserId(productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long productId) {
        productService.deleteById(productId);

        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }

    @GetMapping
    public List<ProductDtoForFindAll> findAll() {
        return productService.findAllValidProducts();
    }
}

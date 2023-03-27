package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;

import java.util.List;

public interface ProductService {

    ProductDtoOut saveProduct(ProductDtoForSave dto);

    List<ProductDtoForFindAll> findAllValidProducts();

    void updateProductById(Long id, ProductDtoForSave dto);

    void deleteProductById(Long id);

    ProductDtoOut findProductById(Long productId);
}

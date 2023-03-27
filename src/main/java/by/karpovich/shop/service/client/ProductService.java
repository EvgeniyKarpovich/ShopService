package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;

import java.util.List;

public interface ProductService {

    ProductDtoOut save(ProductDtoForSave dto);

    List<ProductDtoForFindAll> findAllValidProducts();

    void update(Long id, ProductDtoForSave dto);

    void deleteById(Long id);

    ProductDtoOut findById(Long productId);
}

package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.mapping.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductDtoOut saveProduct(ProductDtoForSave productDto) {
        ProductEntity product = productMapper.mapEntityFromDto(productDto);
        ProductEntity savedProduct = productRepository.save(product);

        return productMapper.mapDtoOutFromEntity(savedProduct);
    }

    //Отображаем только валидные продукты
    public List<ProductDtoForFindAll> findAllValidProducts() {
        return productMapper.mapListDtoForFindAllFromListEntity(productRepository.findAllValidProducts());
    }

    @Override
    @Transactional
    public void updateProductById(Long productId, ProductDtoForSave dto) {
        ProductEntity product = productMapper.mapEntityFromDto(dto);
        product.setId(productId);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProductById(Long productId) {
        if (productRepository.findById(productId).isEmpty()) {
            throw new NotFoundModelException(String.format("Product with id = %s not found", productId));
        }
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDtoOut findProductById(Long productId) {
        return productMapper.mapDtoOutFromEntity(findProductByIdWhichWillReturnModel(productId));
    }

    public ProductEntity findProductByIdWhichWillReturnModel(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException(String.format("Product with id = %s not found", id)));
    }
}

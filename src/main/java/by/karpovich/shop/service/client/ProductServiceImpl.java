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
    public ProductDtoOut save(ProductDtoForSave dto) {
        var entity = productMapper.mapEntityFromDto(dto);
        var savedEntity = productRepository.save(entity);

        return productMapper.mapDtoOutFromEntity(savedEntity);
    }

    //Отображаем только валидные продукты
    public List<ProductDtoForFindAll> findAllValidProducts() {
        return productMapper.mapListDtoForFindAllFromListEntity(productRepository.findAllValidProducts());
    }

    @Override
    @Transactional
    public void update(Long id, ProductDtoForSave dto) {
        ProductEntity entity = productMapper.mapEntityFromDto(dto);
        entity.setId(id);
        productRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (productRepository.findById(id).isPresent()) {
            throw new NotFoundModelException(String.format("Product with id = %s not found", id));
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductDtoOut findById(Long productId) {
        return productMapper.mapDtoOutFromEntity(findProductByIdWhichWillReturnModel(productId));
    }

    public ProductEntity findProductByIdWhichWillReturnModel(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("Product with id = " + id + "not found"));
    }
}

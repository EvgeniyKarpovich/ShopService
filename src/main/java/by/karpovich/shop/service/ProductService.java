package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.mapping.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public void save(ProductDtoForSave dto) {
        ProductEntity entity = productMapper.mapEntityFromDto(dto);

        productRepository.save(entity);
    }

    public ProductDtoOut findById(Long id) {
        ProductEntity entity = productRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("not found"));

        return productMapper.mapDtoOutFromEntity(entity);
    }
}

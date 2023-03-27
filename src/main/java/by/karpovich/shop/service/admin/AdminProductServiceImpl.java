package by.karpovich.shop.service.admin;

import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void doValidProduct(Long productId) {
        if (productRepository.findById(productId).isPresent()) {
            productRepository.doValidProduct(productId);
        } else {
            throw new NotFoundModelException(String.format("Product with id = %s not found", productId));
        }
    }
}

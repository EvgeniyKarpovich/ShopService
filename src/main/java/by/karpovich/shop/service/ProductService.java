package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.exception.NotEnoughMoneyException;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.exception.NotInStockException;
import by.karpovich.shop.exception.NotValidException;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.jpa.repository.DiscountRepository;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.jpa.repository.UserRepository;
import by.karpovich.shop.mapping.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProductDtoOut save(ProductDtoForSave dto) {
        var entity = productMapper.mapEntityFromDto(dto);
        var savedEntity = productRepository.save(entity);

        return productMapper.mapDtoOutFromEntity(savedEntity);
    }

    @Transactional
    public void addDiscount(Long productId, Long discountId) {
        var entity = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundModelException("not found"));

        var discount = discountRepository.findById(discountId).orElseThrow(
                () -> new NotFoundModelException("not found"));

        entity.setDiscount(discount);
        productRepository.save(entity);
    }

    @Transactional
    public void buyProduct(Long userId, Long productId) {
        UserEntity userById = findUserById(userId);
        ProductEntity productById = findProductById(productId);

        Double balance = userById.getBalance();
        Double price = productById.getPrice();
        Double money = productById.getOrganization().getMoney();

        if (price > balance) {
            throw new NotEnoughMoneyException("Not enough money");
        }
        if (productById.getQuantity() < 1) {
            throw new NotInStockException("Not in stock");
        }
        if (!productById.getIsValid().equals(true)) {
            throw new NotValidException("Product is not valid");
        }
        if (productById.getDiscount() != null) {
            int discountPercentage = productById.getDiscount().getDiscountPercentage();
            price = price * discountPercentage / 100;
        }

        userById.setBalance(balance - price);
        userById.getProducts().add(productById);
        productById.setQuantity(productById.getQuantity() - 1);
        productById.getOrganization().setMoney(money + price * 0.90);

        userRepository.save(userById);
        productRepository.save(productById);
    }


    public ProductEntity findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("product not found"));
    }

    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("user not found"));
    }

    public List<ProductDtoForFindAll> findAll() {
        var entities = productRepository.findAll().stream()
                .filter(product -> product.getOrganization().getStatus().equals(StatusOrganization.ACTIVE))
                .toList();

        return productMapper.mapListDtoForFindAllFromListEntity(entities);
    }

    @Transactional
    public void update(Long id,  ProductDtoForSave dto) {
        ProductEntity entity = productMapper.mapEntityFromDto(dto);
        entity.setId(id);
        productRepository.save(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
        }
        throw new NotFoundModelException(String.format("Product with id = %s not found", id));
    }

    public ProductDtoOut findById(Long id) {
        var entity = productRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("not found"));

        return productMapper.mapDtoOutFromEntity(entity);
    }
}

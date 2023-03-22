package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.exception.NotEnoughMoneyException;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.exception.NotInStockException;
import by.karpovich.shop.exception.NotValidException;
import by.karpovich.shop.jpa.entity.*;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.jpa.repository.UserRepository;
import by.karpovich.shop.mapping.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final DiscountService discountService;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public ProductDtoOut save(ProductDtoForSave dto) {
        var entity = productMapper.mapEntityFromDto(dto);
        var savedEntity = productRepository.save(entity);

        return productMapper.mapDtoOutFromEntity(savedEntity);
    }

    @Transactional
    public void addDiscount(List<Long> productsId, Long discountId) {
        DiscountEntity discount = discountService.findDiscountByIdWhichWillReturnModel(discountId);

        for (Long id : productsId) {
            ProductEntity product = findProductByIdWhichWillReturnModel(id);
            product.setDiscount(discount);
            productRepository.save(product);
        }
    }

    @Transactional
    public void buyProduct(Long userId, Long productId) {
        var productById = findProductByIdWhichWillReturnModel(productId);
        var userById = userService.findUserByIdWhichWillReturnModel(userId);

        var balance = userById.getBalance();
        var price = productById.getPrice();
        var money = productById.getOrganization().getMoney();

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
        productById.setDateOfPurchase(Instant.now());

        userRepository.save(userById);
        productRepository.save(productById);
    }

    @Transactional
    public void returnProduct(Long userId, Long productId) {
        UserEntity user = userService.findUserByIdWhichWillReturnModel(userId);

        Optional<ProductEntity> productById = user.getProducts().stream()
                .filter(prId -> prId.getId().equals(productId))
                .findFirst();

        ProductEntity product = productById.orElseThrow(
                () -> new NotFoundModelException("Product not found"));

        OrganizationEntity organization = product.getOrganization();

        if (product.getDateOfPurchase().isBefore(Instant.now().plus(1, ChronoUnit.DAYS))
                && product.getDiscount() == null) {
            user.getProducts().remove(product);
            user.setBalance(user.getBalance() + product.getPrice());
            organization.setMoney(organization.getMoney() - product.getPrice());
        }
        if (product.getDiscount() != null) {
            int discountPercentage = product.getDiscount().getDiscountPercentage();
            user.getProducts().remove(product);
            user.setBalance(user.getBalance() + product.getPrice());
            organization.setMoney(organization.getMoney() - (product.getPrice() + (product.getPrice() / 100 * discountPercentage)));
        }

        organizationRepository.save(organization);
        userRepository.save(user);
    }

    public List<ProductDtoForFindAll> findAll() {
        var entities = productRepository.findAll().stream()
                .filter(product -> product.getOrganization().getStatus().equals(StatusOrganization.ACTIVE))
                .toList();

        return productMapper.mapListDtoForFindAllFromListEntity(entities);
    }

    @Transactional
    public void update(Long id, ProductDtoForSave dto) {
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

    public ProductDtoOut findById(Long productId) {
        return productMapper.mapDtoOutFromEntity(findProductByIdWhichWillReturnModel(productId));
    }

    public ProductEntity findProductByIdWhichWillReturnModel(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("Product with id = " + id + "not found"));
    }
}

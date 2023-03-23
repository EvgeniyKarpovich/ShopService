package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.exception.*;
import by.karpovich.shop.jpa.entity.DiscountEntity;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.jpa.repository.ShopRepository;
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
    private final ShopRepository shopRepository;
    private final ShopService shopService;

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
            var product = findProductByIdWhichWillReturnModel(id);
            product.setDiscount(discount);
            productRepository.save(product);
        }
    }

    @Transactional
    public void buyProduct(Long userId, Long productId) {
        var productById = findProductByIdWhichWillReturnModel(productId);
        var userById = userService.findUserByIdWhichWillReturnModel(userId);

        var shopId = productById.getOrganization().getShop().getId();

        var shop = shopService.findById(shopId);

        var user = productById.getOrganization().getUser();

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
        if (!productById.getOrganization().getStatus().equals(StatusOrganization.ACTIVE)) {
            throw new NotValidOrganization("Organization not valid");
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
        shop.setMoney(price * 0.10);

        shopRepository.save(shop);
        userRepository.save(userById);
        productRepository.save(productById);
    }

    @Transactional
    public void returnProduct(Long userId, Long productId) {
        var user = userService.findUserByIdWhichWillReturnModel(userId);

        Optional<ProductEntity> productById = user.getProducts().stream()
                .filter(prId -> prId.getId().equals(productId))
                .findFirst();

        var product = productById.orElseThrow(
                () -> new NotFoundModelException("Product not found"));

        var shopId = product.getOrganization().getShop().getId();

        var shop = shopService.findById(shopId);

        var price = product.getPrice();
        var balance = user.getBalance();
        var money = product.getOrganization().getMoney();
        var moneyOfShop = shop.getMoney();

        var organization = product.getOrganization();

        if (product.getDateOfPurchase().isBefore(Instant.now().plus(1, ChronoUnit.DAYS))
                && product.getDiscount() != null) {

            int discountPercentage = product.getDiscount().getDiscountPercentage();
            price = (price * discountPercentage / 100);
        }

        user.getProducts().remove(product);
        user.setBalance(balance + price);
        organization.setMoney(money - (price * 0.90));
        shop.setMoney(moneyOfShop - (price * 0.10));
        product.setQuantity(product.getQuantity() + 1);

        shopRepository.save(shop);
        organizationRepository.save(organization);
        userRepository.save(user);
    }

    public List<ProductDtoForFindAll> findAll() {
        var entities = productRepository.findAll().stream()
                .filter(product -> product.getIsValid().equals(true))
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
        if (!productRepository.findById(id).isPresent()) {
            throw new NotFoundModelException(String.format("Product with id = %s not found", id));
        }
        productRepository.deleteById(id);
    }

    public ProductDtoOut findById(Long productId) {
        return productMapper.mapDtoOutFromEntity(findProductByIdWhichWillReturnModel(productId));
    }

    public ProductEntity findProductByIdWhichWillReturnModel(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("Product with id = " + id + "not found"));
    }
}

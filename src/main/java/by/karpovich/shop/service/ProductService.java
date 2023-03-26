package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.product.ProductDtoForSave;
import by.karpovich.shop.api.dto.product.ProductDtoOut;
import by.karpovich.shop.exception.*;
import by.karpovich.shop.jpa.entity.DiscountEntity;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.jpa.repository.*;
import by.karpovich.shop.mapping.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    private final DiscountRepository discountRepository;

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

    //Покупка продукта(товар должен быть валидным, баланс больше цены товара, кол-во на складе > 1,
    // организаниця, которая продает товар должна быть валидной. С учетом наличия скидки)  (10% маржи идет магазину)
    @Transactional
    public void buyProduct(String authorization, Long productId) {
        Long userId = userService.getUserIdFromToken(authorization);

        var product = findProductByIdWhichWillReturnModel(productId);
        var user = userService.findUserByIdWhichWillReturnModel(userId);
        var organization = product.getOrganization();
        var shop = shopService.findById(product.getOrganization().getShop().getId());
        var productPrice = calculateAmountWithDiscount(product);

        if (productPrice > user.getBalance()) {
            throw new NotEnoughMoneyException("Not enough money");
        }
        if (product.getQuantity() < 1) {
            throw new NotInStockException("Not in stock");
        }
        if (!product.getIsValid().equals(true)) {
            throw new NotValidException("Product is not valid");
        }
        if (!product.getOrganization().getStatus().equals(StatusOrganization.ACTIVE)) {
            throw new NotValidOrganization("Organization not valid");
        }

        user.setBalance(user.getBalance() - productPrice);
        user.getProducts().add(product);
        product.setQuantity(product.getQuantity() - 1);
        organization.setMoney(organization.getMoney() + productPrice * 0.90);
        product.setDateOfPurchase(Instant.now());
        shop.setMoney(productPrice * 0.10);
        product.setPriceWithDiscount(productPrice);

        shopRepository.save(shop);
        userRepository.save(user);
        productRepository.save(product);
        organizationRepository.save(organization);
    }

    @Transactional
    public void returnProduct(String authorization, Long productId) {
        Long userId = userService.getUserIdFromToken(authorization);

        var user = userService.findUserByIdWhichWillReturnModel(userId);
        var product = findProductById(user, productId);
        var shop = shopService.findById(product.getOrganization().getShop().getId());
        var productPrice = product.getPriceWithDiscount();
        var organization = product.getOrganization();

        if (product.getDateOfPurchase().plus(1, ChronoUnit.DAYS).isBefore(Instant.now())) {
            throw new NotValidException("More than a day has passed since the purchase, the goods can not be returned");
        } else {
            user.getProducts().remove(product);
            user.setBalance(user.getBalance() + productPrice);
            organization.setMoney(organization.getMoney() - (productPrice * 0.90));
            shop.setMoney(shop.getMoney() - (productPrice * 0.10));
            product.setQuantity(product.getQuantity() + 1);

            shopRepository.save(shop);
            organizationRepository.save(organization);
            userRepository.save(user);
            productRepository.save(product);
        }
    }

    private ProductEntity findProductById(UserEntity user, Long productId) {
        return user.getProducts().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundModelException("Product not found"));
    }

    //Рассчитываем финальную стоимость товара
    private double calculateAmountWithDiscount(ProductEntity entity) {
        Double productPrice = entity.getPrice();
        if (entity.getDiscount() != null
                && Instant.now().isBefore(entity.getDiscount().getFinishDiscount())
                && Instant.now().isAfter(entity.getDiscount().getStartDiscount())) {
            int discountPercentage = entity.getDiscount().getDiscountPercentage();
            productPrice = productPrice * discountPercentage / 100;
        }
        return productPrice;
    }

    //Отображаем только валидные продукты
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
    public void deleteDiscountFromProducts(List<Long> productsId, Long id) {
        if (discountRepository.findById(id).isEmpty()) {
            throw new NotFoundModelException(String.format("Discount with id = %s not found", id));
        } else {
            for (Long idProduct : productsId) {
                ProductEntity product = findProductByIdWhichWillReturnModel(idProduct);
                product.setDiscount(null);
            }
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (productRepository.findById(id).isPresent()) {
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

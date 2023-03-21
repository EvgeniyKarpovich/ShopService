package by.karpovich.shop.service;

import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class Logic {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public void buyProduct(Long userId, Long productId) {
        UserEntity userById = findUserById(userId);
        ProductEntity productById = findProductById(productId);

        Double balance = userById.getBalance();
        Double price = productById.getPrice();
        Double money = productById.getOrganization().getMoney();


        if (price > balance) {
            throw new RuntimeException("not enough money");
        }
        if (productById.getQuantity() < 1) {
            throw new RuntimeException("not in stock");
        }
        if (!productById.getIsValid().equals(true)) {
            throw new RuntimeException("product is not valid");
        }
        if (productById.getDiscount() != null) {
            int discountPercentage = productById.getDiscount().getDiscountPercentage();
            price = price * discountPercentage / 100;
        }
//        userRepository.decrementBalance(userId, price);
//        List<ProductEntity> products = userById.getProducts();
//        products.add(productById);
//        productRepository.decrementQuantity(productId);
//        userById.setProducts(products);
//        userRepository.save(userById);

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
}

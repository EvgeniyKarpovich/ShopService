package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.authentification.JwtResponse;
import by.karpovich.shop.api.dto.authentification.LoginForm;
import by.karpovich.shop.api.dto.authentification.RegistrationForm;
import by.karpovich.shop.api.dto.notification.NotificationDto;
import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserForUpdate;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;
import by.karpovich.shop.exception.*;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.jpa.entity.UserEntity;
import by.karpovich.shop.jpa.repository.OrganizationRepository;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.jpa.repository.ShopRepository;
import by.karpovich.shop.jpa.repository.UserRepository;
import by.karpovich.shop.mapping.NotificationMapper;
import by.karpovich.shop.mapping.ProductMapper;
import by.karpovich.shop.mapping.UserMapper;
import by.karpovich.shop.security.JwtUtils;
import by.karpovich.shop.security.UserDetailsImpl;
import by.karpovich.shop.service.admin.AdminShopServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final AdminShopServiceImpl shopService;
    private final ProductServiceImpl productService;
    private final ShopRepository shopRepository;
    private final OrganizationRepository organizationRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void signUp(RegistrationForm dto) {

        userRepository.save(userMapper.mapEntityFromDtoForRegForm(dto));
    }

    @Override
    @Transactional
    public JwtResponse signIn(LoginForm loginForm) {
        String username = loginForm.getUsername();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        UserEntity userByName = findUserByName(username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userByName.getUsername(), userByName.getId());

        return JwtResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(mapStringRolesFromUserDetails(userDetails))
                .type("Bearer")
                .token(jwt)
                .build();
    }

    private List<String> mapStringRolesFromUserDetails(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @Transactional
    public void buyProduct(String authorization, Long productId) {
        Long userId = getUserIdFromToken(authorization);

        var product = productService.findProductByIdWhichWillReturnModel(productId);
        var user = findUserByIdWhichWillReturnModel(userId);
        var organization = product.getOrganization();
        var shop = shopService.findShopById(product.getOrganization().getShop().getId());
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
        Long userId = getUserIdFromToken(authorization);

        var user = findUserByIdWhichWillReturnModel(userId);
        var product = findProductById(user, productId);
        var shop = shopService.findShopById(product.getOrganization().getShop().getId());
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

    @Override
    public UserFullDtoOut findUserByIdFromToken(String token) {
        Long userIdFromToken = getUserIdFromToken(token);

        return userMapper.mapUserFullDtoFromModel(findUserByIdWhichWillReturnModel(userIdFromToken));
    }

    @Override
    @Transactional
    public void deleteUserById(String token) {
        Long userIdFromToken = getUserIdFromToken(token);
        if (userRepository.findById(userIdFromToken).isEmpty()) {
            throw new NotFoundModelException(String.format("User with id = %s not found", userIdFromToken));
        }
        userRepository.deleteById(userIdFromToken);
    }

    @Override
    public List<ProductDtoForFindAll> findUserProducts(String authorization) {
        Long userIdFromToken = getUserIdFromToken(authorization);
        List<ProductEntity> products = findUserByIdWhichWillReturnModel(userIdFromToken).getProducts();

        return productMapper.mapListDtoForFindAllFromListEntity(products);
    }

    @Override
    @Transactional
    public UserFullDtoOut updateUserById(String token, UserForUpdate dto) {
        Long userIdFromToken = getUserIdFromToken(token);

        UserEntity user = userMapper.mapEntityFromUpdateDto(dto);
        user.setId(userIdFromToken);
        UserEntity updatedUser = userRepository.save(user);

        log.info("method update - the user {} updated", updatedUser.getUsername());
        return userMapper.mapUserFullDtoFromModel(updatedUser);
    }

    @Override
    public List<NotificationDto> findAllUserNotifications(String token) {
        Long userIdFromToken = getUserIdFromToken(token);
        UserEntity user = findUserByIdWhichWillReturnModel(userIdFromToken);

        return notificationMapper.mapListNotificationDtoFromListEntity(user.getNotifications());
    }

    public UserEntity findUserByIdWhichWillReturnModel(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("User with id = " + id + "not found"));
    }

    public UserEntity findUserByName(String username) {
        Optional<UserEntity> userByName = userRepository.findByUsername(username);

        var entity = userByName.orElseThrow(
                () -> new NotFoundModelException(String.format("User with username = %s not found", username))
        );
        log.info("method findByName -  User with username = {} found", entity.getUsername());
        return entity;
    }

    public UserEntity findUserEntityByIdFromToken(String token) {
        Long userIdFromToken = getUserIdFromToken(token);

        return findUserByIdWhichWillReturnModel(userIdFromToken);
    }

    public Long getUserIdFromToken(String authorization) {
        String token = authorization.substring(7);
        String userIdFromJWT = jwtUtils.getUserIdFromJWT(token);
        return Long.parseLong(userIdFromJWT);
    }
}

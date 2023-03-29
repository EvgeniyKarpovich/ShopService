package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.authentification.JwtResponse;
import by.karpovich.shop.api.dto.authentification.LoginForm;
import by.karpovich.shop.api.dto.authentification.RegistrationForm;
import by.karpovich.shop.api.dto.notification.NotificationDto;
import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserForUpdate;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;

import java.util.List;

public interface UserService {

    List<NotificationDto> findAllUserNotifications(String token);

    void signUp(RegistrationForm registrationForm);

    JwtResponse signIn(LoginForm loginForm);

    UserFullDtoOut updateUserById(String token, UserForUpdate dto);

    void deleteUserById(String token);

    List<ProductDtoForFindAll> findUserProducts(String token);

    void buyProduct(String token, Long productId);

    void returnProduct(String token, Long productId);

    UserFullDtoOut findUserByIdFromToken(String token);
}

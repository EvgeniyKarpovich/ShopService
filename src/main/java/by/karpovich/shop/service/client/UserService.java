package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.authentification.JwtResponse;
import by.karpovich.shop.api.dto.authentification.LoginForm;
import by.karpovich.shop.api.dto.authentification.RegistrationForm;
import by.karpovich.shop.api.dto.notification.NotificationDto;
import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserForUpdate;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;
import by.karpovich.shop.jpa.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<NotificationDto> findAllUserNotifications(String token);

    UserEntity findUserByIdWhichWillReturnModel(Long userId);

    void signUp(RegistrationForm registrationForm);

    JwtResponse signIn(LoginForm loginForm);

    UserEntity findUserByName(String userName);

    UserFullDtoOut updateUserById(String token, UserForUpdate dto);

    void deleteUserById(String token);

    List<ProductDtoForFindAll> findUserProducts(String authorization);

    void buyProduct(String authorization, Long productId);

    void returnProduct(String authorization, Long productId);

    UserFullDtoOut findUserById(String token);
}

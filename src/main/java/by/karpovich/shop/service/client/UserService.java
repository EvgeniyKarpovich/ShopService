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

    UserEntity findByName(String userName);

    UserFullDtoOut updateById(String token, UserForUpdate dto);

    void deleteById(String token);

    List<ProductDtoForFindAll> userProducts(String authorization);

    void buyProduct(String authorization, Long productId);

    void returnProduct(String authorization, Long productId);

    UserFullDtoOut findUserById(String token);
}

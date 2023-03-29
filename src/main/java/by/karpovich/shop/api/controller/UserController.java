package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.notification.NotificationDto;
import by.karpovich.shop.api.dto.product.ProductDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserForUpdate;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;
import by.karpovich.shop.service.client.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PutMapping("/returns/{productId}")
    public void returnProduct(@RequestHeader(value = "Authorization") String authorization,
                              @PathVariable("productId") Long productId) {

        userService.returnProduct(authorization, productId);
    }

    @PutMapping("/buy/{productId}")
    public void buy(@RequestHeader(value = "Authorization") String authorization,
                    @PathVariable("productId") Long productId) {

        userService.buyProduct(authorization, productId);
    }

    @GetMapping("/notifications")
    public List<NotificationDto> findAllNotification(@RequestHeader(value = "Authorization") String authorization) {

        return userService.findAllUserNotifications(authorization);
    }

    @GetMapping
    public UserFullDtoOut findById(@RequestHeader(value = "Authorization") String authorization) {
        return userService.findUserByIdFromToken(authorization);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserForUpdate dto,
                                    @PathVariable("id") String authorization) {
        userService.updateUserById(authorization, dto);
        return new ResponseEntity<>("User successfully updated", HttpStatus.OK);
    }

    @GetMapping("/products")
    public List<ProductDtoForFindAll> userProducts(@RequestHeader(value = "Authorization") String authorization) {

        return userService.findUserProducts(authorization);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String authorization) {
        userService.deleteUserById(authorization);

        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }
}

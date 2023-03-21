package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.notification.NotificationDto;
import by.karpovich.shop.api.dto.user.UserDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserForUpdate;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;
import by.karpovich.shop.jpa.entity.NotificationEntity;
import by.karpovich.shop.service.UserService;
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

    private final UserService userService;

    @GetMapping("/notifications/{userId}")
    public List<NotificationDto> findAllNotification(@PathVariable("userId") Long userId) {
        return userService.findAllNotification(userId);
    }

    @GetMapping("/{id}")
    public UserFullDtoOut findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<UserDtoForFindAll> findAll() {
        return userService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserForUpdate dto,
                                    @PathVariable("id") Long id) {
        userService.update(id, dto);
        return new ResponseEntity<>("User successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);

        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }
}

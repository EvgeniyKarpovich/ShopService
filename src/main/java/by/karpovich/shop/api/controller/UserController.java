package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.user.UserDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserForUpdate;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;
import by.karpovich.shop.jpa.repository.UserRepository;
import by.karpovich.shop.service.Logic;
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
    private final UserRepository userRepository;
    private final Logic logic;

    @PutMapping("/buy/{userId}/{productId}")
    public void buy(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) {
        logic.buyProduct(userId, productId);
    }

    @PutMapping("/balance/{id}/{price}")
    public void decrementBalance(@PathVariable("id") Long userId, @PathVariable("price") Double price) {
        userRepository.decrementBalance(userId, price);
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

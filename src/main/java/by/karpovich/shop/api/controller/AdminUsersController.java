package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.notification.NotificationDtoForSend;
import by.karpovich.shop.api.dto.user.UserDtoForFindAll;
import by.karpovich.shop.api.dto.user.UserFullDtoOut;
import by.karpovich.shop.jpa.entity.StatusUser;
import by.karpovich.shop.service.admin.AdminNotificationServiceImpl;
import by.karpovich.shop.service.admin.AdminUserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins/users")
@RequiredArgsConstructor
public class AdminUsersController {

    private final AdminUserServiceImpl adminUserService;
    private final AdminNotificationServiceImpl adminNotificationService;

    @PostMapping
    public void sendNotificationsToUsers(@Valid @RequestBody NotificationDtoForSend notificationDto) {
        adminNotificationService.sendNotificationsToUsers(notificationDto);
    }

    @PutMapping("/balances/inc/{id}/{sum}")
    public void addBalance(@PathVariable("id") Long userId,
                           @PathVariable("sum") Double sum) {
        adminUserService.addBalance(userId, sum);
    }

    @PutMapping("/statuses/frozen/{id}/{status}")
    public void setFrozenStatusUser(@PathVariable("id") Long id, @PathVariable("status") StatusUser status) {
        adminUserService.setUserStatus(id, StatusUser.FROZEN);
    }

    @PutMapping("/statuses/deleted/{id}/{status}")
    public void setDeletedStatusUser(@PathVariable("id") Long id, @PathVariable("status") StatusUser status) {
        adminUserService.setUserStatus(id, StatusUser.DELETED);
    }

    @PutMapping("/statuses/active/{id}/{status}")
    public void setActiveStatusUser(@PathVariable("id") Long id, @PathVariable("status") StatusUser status) {
        adminUserService.setUserStatus(id, StatusUser.ACTIVE);
    }

    @GetMapping
    public List<UserDtoForFindAll> findAll() {
        return adminUserService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserFullDtoOut findUserById(@PathVariable("id") Long userId) {
        return adminUserService.findUserById(userId);
    }
}

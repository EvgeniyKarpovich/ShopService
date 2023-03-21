package by.karpovich.shop.api.controller;

import by.karpovich.shop.jpa.entity.StatusOrganization;
import by.karpovich.shop.jpa.entity.StatusUser;
import by.karpovich.shop.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/valid/{id}")
    public void doProductValid(@PathVariable("id") Long productId) {
        adminService.doProductValid(productId);
    }

    @PutMapping("/balance/inc/{id}/{sum}")
    public void addBalance(@PathVariable("id") Long userId,
                           @PathVariable("sum") Double sum) {
        adminService.addBalance(userId, sum);
    }

    @PutMapping("/statuses/org/frozen/{id}/{status}")
    public void setFrozenStatusOrg(@PathVariable("id") Long id, @PathVariable("status") StatusOrganization status) {
        adminService.setOrganizationStatus(id, StatusOrganization.FROZEN);
    }

    @PutMapping("/statuses/org/deleted/{id}/{status}")
    public void setDeletedStatusOrg(@PathVariable("id") Long id, @PathVariable("status") StatusOrganization status) {
        adminService.setOrganizationStatus(id, StatusOrganization.DELETED);
    }

    @PutMapping("/statuses/active/org/{id}/{status}")
    public void setActiveStatusOrg(@PathVariable("id") Long id, @PathVariable("status") StatusOrganization status) {
        adminService.setOrganizationStatus(id, StatusOrganization.ACTIVE);
    }

    @PutMapping("/statuses/user/frozen/{id}/{status}")
    public void setFrozenStatusUser(@PathVariable("id") Long id, @PathVariable("status") StatusUser status) {
        adminService.setUserStatus(id, StatusUser.FROZEN);
    }

    @PutMapping("/statuses/user/deleted/{id}/{status}")
    public void setDeletedStatusUser(@PathVariable("id") Long id, @PathVariable("status") StatusUser status) {
        adminService.setUserStatus(id, StatusUser.DELETED);
    }

    @PutMapping("/statuses/user/active/{id}/{status}")
    public void setActiveStatusUser(@PathVariable("id") Long id, @PathVariable("status") StatusUser status) {
        adminService.setUserStatus(id, StatusUser.ACTIVE);
    }

    @PutMapping("/notifications/{userId}/{notificationId}")
    public void sendNotification(@PathVariable("userId") Long userId, @PathVariable("notificationId") Long notificationId) {
        adminService.sendNotification(userId, notificationId);
    }
}

package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.notification.NotificationForSaveDto;
import by.karpovich.shop.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public void save(@RequestBody NotificationForSaveDto dto) {
        notificationService.save(dto);
    }
}

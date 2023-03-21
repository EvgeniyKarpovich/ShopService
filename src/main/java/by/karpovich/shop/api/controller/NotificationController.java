package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.notification.NotificationDto;
import by.karpovich.shop.api.dto.notification.NotificationForSaveDto;
import by.karpovich.shop.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public void save(@RequestBody NotificationForSaveDto dto) {
        notificationService.save(dto);
    }

    @GetMapping
    public List<NotificationDto> findAllByUserId(@PathVariable("id") Long userId) {
        return notificationService.findAllByUserId(userId);
    }
}

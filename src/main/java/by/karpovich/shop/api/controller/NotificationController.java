package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.notification.NotificationDto;
import by.karpovich.shop.api.dto.notification.NotificationForSaveDto;
import by.karpovich.shop.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody NotificationForSaveDto dto) {
        notificationService.save(dto);

        return new ResponseEntity<>("Notification saved successfully", HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public List<NotificationDto> findAllNotification(@PathVariable("userId") Long userId) {
        return notificationService.findAllNotification(userId);
    }
}

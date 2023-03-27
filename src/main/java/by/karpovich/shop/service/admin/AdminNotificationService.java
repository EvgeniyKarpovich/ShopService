package by.karpovich.shop.service.admin;

import by.karpovich.shop.api.dto.notification.NotificationDtoForSend;

public interface AdminNotificationService {

    void sendNotificationsToUsers(NotificationDtoForSend notificationDto);
}

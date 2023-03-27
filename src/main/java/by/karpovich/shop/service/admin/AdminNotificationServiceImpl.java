package by.karpovich.shop.service.admin;

import by.karpovich.shop.api.dto.notification.NotificationDtoForSend;
import by.karpovich.shop.jpa.repository.NotificationRepository;
import by.karpovich.shop.mapping.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminNotificationServiceImpl implements AdminNotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void sendNotificationsToUsers(NotificationDtoForSend notificationsDto) {
        notificationRepository.save(notificationMapper.mapEntityFromDto(notificationsDto));
    }
}

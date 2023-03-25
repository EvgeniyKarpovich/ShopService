package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.notification.NotificationDto;
import by.karpovich.shop.api.dto.notification.NotificationForSaveDto;
import by.karpovich.shop.jpa.repository.NotificationRepository;
import by.karpovich.shop.mapping.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserService userService;

    @Transactional
    public void save(NotificationForSaveDto dto) {
        var entity = notificationMapper.mapEntityFromDto(dto);
        notificationRepository.save(entity);
    }

    //Отображаем все уведомления конкретного пользователя
    public List<NotificationDto> findAllNotification(String authorization) {
        var userEntity = userService.findUserByIdWhichWillReturnModel(userService.getUserIdFromToken(authorization));

        return notificationMapper.mapListNotificationDtoFromListEntity(userEntity.getNotifications());
    }
}

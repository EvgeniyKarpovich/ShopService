package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.notification.NotificationDto;
import by.karpovich.shop.api.dto.notification.NotificationForSaveDto;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.NotificationEntity;
import by.karpovich.shop.jpa.entity.StatusUser;
import by.karpovich.shop.jpa.repository.NotificationRepository;
import by.karpovich.shop.mapping.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Transactional
    public void save(NotificationForSaveDto dto) {
        var entity = notificationMapper.mapEntityFromDto(dto);
        notificationRepository.save(entity);
    }

    public NotificationEntity findNotificationByIdWhichWillReturnModel(Long id) {
        Optional<NotificationEntity> model = notificationRepository.findById(id);

        return model.orElseThrow(
                () -> new NotFoundModelException("Notification with ID = " + id + " not found"));
    }

    public List<NotificationDto> findAllByUserId(Long userId) {
        return notificationMapper.mapListNotificationDtoFromListEntity(notificationRepository.findAllByUserId(userId));
    }
}

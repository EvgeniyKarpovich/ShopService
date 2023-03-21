package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.notification.NotificationDto;
import by.karpovich.shop.api.dto.notification.NotificationForSaveDto;
import by.karpovich.shop.jpa.entity.NotificationEntity;
import by.karpovich.shop.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationMapper {

    public NotificationEntity mapEntityFromDto(NotificationForSaveDto dto) {
        if (dto == null) {
            return null;
        }

        return NotificationEntity.builder()
                .name(dto.getName())
                .message(dto.getMessage())
                .build();
    }

    public NotificationDto mapDtoFromEntity(NotificationEntity entity) {
        if (entity == null) {
            return null;
        }

        return NotificationDto.builder()
                .name(entity.getName())
                .recipient(entity.getUser().getUsername())
                .from("Administration")
                .message(entity.getMessage())
                .date(Utils.mapStringFromInstant(entity.getDateOfCreation()))
                .build();
    }

    public List<NotificationDto> mapListNotificationDtoFromListEntity(List<NotificationEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<NotificationDto> dtos = new ArrayList<>();

        for (NotificationEntity entity : entities) {
            dtos.add(mapDtoFromEntity(entity));
        }

        return dtos;
    }
}

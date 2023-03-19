package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.characteristic.CharacteristicDto;
import by.karpovich.shop.jpa.entity.CharacteristicEntity;
import org.springframework.stereotype.Component;

@Component
public class CharacteristicMapper {

    public CharacteristicDto mapDtoFromEntity(CharacteristicEntity entity) {
        if (entity == null) {
            return null;
        }

        return CharacteristicDto.builder()
                .weight(entity.getWeight())
                .build();
    }
}

package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.characteristic.CharacteristicDto;
import by.karpovich.shop.jpa.entity.CharacteristicEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public CharacteristicEntity mapEntityFromDto(CharacteristicDto dto) {
        if (dto == null) {
            return null;
        }

        return CharacteristicEntity.builder()
                .weight(dto.getWeight())
                .build();
    }

    public List<CharacteristicDto> mapListDtoFromListEntity(List<CharacteristicEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<CharacteristicDto> dtos = new ArrayList<>();

        for (CharacteristicEntity entity : entities) {
            dtos.add(mapDtoFromEntity(entity));
        }

        return dtos;
    }
}

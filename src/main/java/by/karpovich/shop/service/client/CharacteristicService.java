package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.characteristic.CharacteristicDto;
import by.karpovich.shop.jpa.entity.CharacteristicEntity;

import java.util.List;

public interface CharacteristicService {

    CharacteristicDto saveCharacteristic(CharacteristicDto dto);

    CharacteristicDto findCharacteristicById(Long id);

    List<CharacteristicDto> findAllCharacteristics();

    CharacteristicDto updateCharacteristicsById(Long id, CharacteristicDto dto);

    void deleteCharacteristicById(Long id);

    CharacteristicEntity findCharacterByIdWhichWillReturnModel(Long id);
}

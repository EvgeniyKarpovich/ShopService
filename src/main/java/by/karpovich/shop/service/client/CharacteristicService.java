package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.characteristic.CharacteristicDto;

import java.util.List;

public interface CharacteristicService {

    CharacteristicDto saveCharacteristic(CharacteristicDto dto);

    CharacteristicDto findCharacteristicById(Long id);

    List<CharacteristicDto> findAllCharacteristics();

    CharacteristicDto updateCharacteristicsById(Long id, CharacteristicDto dto);

    void deleteCharacteristicById(Long id);
}

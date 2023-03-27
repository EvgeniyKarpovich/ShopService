package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.characteristic.CharacteristicDto;
import by.karpovich.shop.jpa.entity.CharacteristicEntity;

import java.util.List;

public interface CharacteristicService {

    CharacteristicDto save(CharacteristicDto dto);

    CharacteristicDto findById(Long id);

    List<CharacteristicDto> findAll();

    CharacteristicDto update(Long id, CharacteristicDto dto);

    void deleteById(Long id);

    CharacteristicEntity findCharacterByIdWhichWillReturnModel(Long id);
}

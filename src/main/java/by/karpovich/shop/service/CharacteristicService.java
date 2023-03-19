package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.characteristic.CharacteristicDto;
import by.karpovich.shop.jpa.entity.CharacteristicEntity;
import by.karpovich.shop.jpa.repository.CharacteristicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacteristicService {

    private final CharacteristicRepository characteristicRepository;

    public void save(CharacteristicDto dto) {

        CharacteristicEntity entity = new CharacteristicEntity();

        entity.setWeight(dto.getWeight());
        characteristicRepository.save(entity);
    }
}

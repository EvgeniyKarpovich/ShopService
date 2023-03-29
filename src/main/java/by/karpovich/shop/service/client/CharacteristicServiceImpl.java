package by.karpovich.shop.service.client;

import by.karpovich.shop.api.dto.characteristic.CharacteristicDto;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.CharacteristicEntity;
import by.karpovich.shop.jpa.repository.CharacteristicRepository;
import by.karpovich.shop.mapping.CharacteristicMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {

    private final CharacteristicRepository characteristicRepository;
    private final CharacteristicMapper characteristicMapper;

    @Override
    @Transactional
    public CharacteristicDto saveCharacteristic(CharacteristicDto dto) {
        var characteristic = characteristicMapper.mapEntityFromDto(dto);
        var savedCharacteristic = characteristicRepository.save(characteristic);

        log.info("method save - characteristic with id = {} saved", savedCharacteristic.getId());
        return characteristicMapper.mapDtoFromEntity(savedCharacteristic);
    }

    @Override
    public CharacteristicDto findCharacteristicById(Long id) {
        var characteristic = characteristicRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException(String.format("characteristic with id = %s not found", id)));

        log.info("method findById - characteristic found with id = {} ", characteristic.getId());
        return characteristicMapper.mapDtoFromEntity(characteristic);
    }

    @Override
    public List<CharacteristicDto> findAllCharacteristics() {
        var characteristics = characteristicRepository.findAll();

        log.info("method findAll - characteristic found  = {} ", characteristics.size());
        return characteristicMapper.mapListDtoFromListEntity(characteristics);
    }

    @Override
    @Transactional
    public CharacteristicDto updateCharacteristicsById(Long id, CharacteristicDto dto) {
        var characteristic = characteristicMapper.mapEntityFromDto(dto);
        characteristic.setId(id);
        var updatedEntity = characteristicRepository.save(characteristic);

        log.info("method update - characteristic with id = {} updated", updatedEntity.getId());
        return characteristicMapper.mapDtoFromEntity(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteCharacteristicById(Long id) {
        if (characteristicRepository.findById(id).isEmpty()) {
            throw new NotFoundModelException(String.format("characteristic with id = %s not found", id));
        } else {
            characteristicRepository.deleteById(id);
        }
        log.info("method deleteById - characteristic with id = {} deleted", id);
    }

    public CharacteristicEntity findCharacterByIdWhichWillReturnModel(Long id) {
        return characteristicRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("Characteristic with id = " + id + "not found"));
    }
}

package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.characteristic.CharacteristicDto;
import by.karpovich.shop.exception.NotFoundModelException;
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
public class CharacteristicService {

    private final CharacteristicRepository characteristicRepository;
    private final CharacteristicMapper characteristicMapper;

    @Transactional
    public CharacteristicDto save(CharacteristicDto dto) {
        var entity = characteristicMapper.mapEntityFromDto(dto);
        var savedEntity = characteristicRepository.save(entity);

        log.info("method save - characteristic with id = {} saved", savedEntity.getId());
        return characteristicMapper.mapDtoFromEntity(savedEntity);
    }

    public CharacteristicDto findById(Long id) {
        var entity = characteristicRepository.findById(id);
        var country = entity.orElseThrow(
                () -> new NotFoundModelException(String.format("characteristic with id = %s not found", id)));

        log.info("method findById - characteristic found with id = {} ", country.getId());
        return characteristicMapper.mapDtoFromEntity(country);
    }

    public List<CharacteristicDto> findAll() {
        var entities = characteristicRepository.findAll();

        log.info("method findAll - characteristic found  = {} ", entities.size());
        return characteristicMapper.mapListDtoFromListEntity(entities);
    }

    @Transactional
    public CharacteristicDto update(Long id, CharacteristicDto dto) {
        var entity = characteristicMapper.mapEntityFromDto(dto);
        entity.setId(id);
        var updatedEntity = characteristicRepository.save(entity);

        log.info("method update - characteristic with id = {} updated", updatedEntity.getId());
        return characteristicMapper.mapDtoFromEntity(updatedEntity);
    }

    @Transactional
    public void deleteById(Long id) {
        if (characteristicRepository.findById(id).isPresent()) {
            characteristicRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format("characteristic with id = %s not found", id));
        }
        log.info("method deleteById - characteristic with id = {} deleted", id);
    }
}

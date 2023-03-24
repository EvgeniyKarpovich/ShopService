package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.discount.DiscountDto;
import by.karpovich.shop.api.dto.discount.DiscountDtoOut;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.DiscountEntity;
import by.karpovich.shop.jpa.repository.DiscountRepository;
import by.karpovich.shop.mapping.DiscountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;

    @Transactional
    public DiscountDtoOut save(DiscountDto dto) {
        var entity = discountMapper.mapEntityFromDto(dto);

        log.info("method save - Discount with name {} saved", entity.getName());
        return discountMapper.mapDtoFromEntity(discountRepository.save(entity));
    }

    public DiscountDtoOut findById(Long id) {
        var entity = discountRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("not found"));

        log.info("method findById - Discount found with id = {} ", entity.getId());
        return discountMapper.mapDtoFromEntity(entity);
    }

    public List<DiscountDtoOut> findAll() {
        var entities = discountRepository.findAll();

        log.info("method findAll - Discounts found  = {} ", entities.size());
        return discountMapper.mapListDtoOutFromListEntities(entities);
    }

    @Transactional
    public DiscountDtoOut update(DiscountDto dto, Long id) {
        var entity = discountMapper.mapEntityFromDto(dto);
        entity.setId(id);
        var updatedEntity = discountRepository.save(entity);

        log.info("method update - Discount {} updated", updatedEntity.getName());
        return discountMapper.mapDtoFromEntity(updatedEntity);
    }

    public DiscountEntity findDiscountByIdWhichWillReturnModel(Long id) {
        return discountRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("Discount with id = " + id + "not found"));
    }
}

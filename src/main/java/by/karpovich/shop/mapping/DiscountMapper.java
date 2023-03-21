package by.karpovich.shop.mapping;

import by.karpovich.shop.api.dto.discount.DiscountDto;
import by.karpovich.shop.api.dto.discount.DiscountDtoOut;
import by.karpovich.shop.jpa.entity.DiscountEntity;
import by.karpovich.shop.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiscountMapper {

    public DiscountEntity mapEntityFromDto(DiscountDto dto) {
        if (dto == null) {
            return null;
        }

        return DiscountEntity.builder()
                .name(dto.getName())
                .discountPercentage(dto.getDiscountPercentage())
                .startDiscount(dto.getStartDiscount())
                .finishDiscount(dto.getFinishDiscount())
                .build();
    }

    public DiscountDtoOut mapDtoFromEntity(DiscountEntity entity) {
        if (entity == null) {
            return null;
        }

        return DiscountDtoOut.builder()
                .name(entity.getName())
                .discountPercentage(entity.getDiscountPercentage())
                .startDiscount(Utils.mapStringFromInstant(entity.getStartDiscount()))
                .finishDiscount(Utils.mapStringFromInstant(entity.getFinishDiscount()))
                .build();
    }

    public List<DiscountDtoOut> mapListDtoOutFromListEntities(List<DiscountEntity> entities) {
        if (entities == null) {
            return null;
        }

        List<DiscountDtoOut> dtos = new ArrayList<>();

        for (DiscountEntity entity : entities) {
            dtos.add(mapDtoFromEntity(entity));
        }

        return dtos;
    }
}

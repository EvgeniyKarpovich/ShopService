package by.karpovich.shop.api.dto.product;

import by.karpovich.shop.api.dto.characteristic.CharacteristicDto;
import by.karpovich.shop.api.dto.discount.DiscountDto;
import by.karpovich.shop.api.dto.discount.DiscountDtoOut;
import by.karpovich.shop.jpa.entity.CharacteristicEntity;
import by.karpovich.shop.jpa.entity.CommentEntity;
import by.karpovich.shop.jpa.entity.DiscountEntity;
import by.karpovich.shop.jpa.entity.OrganizationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoOut {

    private String name;

    private String description;

    private String organizationName;

    private Double price;

    private Integer quantity;

    private DiscountDtoOut discount;

    private List<CommentEntity> comments = new ArrayList<>();

    private List<String> keywords = new ArrayList<>();

    private CharacteristicDto characteristic;

}

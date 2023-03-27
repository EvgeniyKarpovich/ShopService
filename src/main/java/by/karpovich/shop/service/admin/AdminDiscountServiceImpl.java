package by.karpovich.shop.service.admin;

import by.karpovich.shop.api.dto.discount.DiscountDto;
import by.karpovich.shop.api.dto.discount.DiscountDtoOut;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.DiscountEntity;
import by.karpovich.shop.jpa.entity.ProductEntity;
import by.karpovich.shop.jpa.repository.DiscountRepository;
import by.karpovich.shop.jpa.repository.ProductRepository;
import by.karpovich.shop.mapping.DiscountMapper;
import by.karpovich.shop.service.client.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminDiscountServiceImpl implements AdminDiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    private final ProductServiceImpl productService;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void saveDiscount(DiscountDto dto) {
        var entity = discountMapper.mapEntityFromDto(dto);

        log.info("method save - Discount with name {} saved", entity.getName());
        discountRepository.save(entity);
    }

    @Override
    @Transactional
    public void addDiscount(List<Long> productsId, Long discountId) {
        DiscountEntity discount = findDiscountByIdWhichWillReturnModel(discountId);

        for (Long id : productsId) {
            ProductEntity product = productService.findProductByIdWhichWillReturnModel(id);
            product.setDiscount(discount);
            productRepository.save(product);
        }
    }

    @Override
    @Transactional
    public void deleteDiscountFromProducts(List<Long> productsId, Long discountId) {
        if (discountRepository.findById(discountId).isEmpty()) {
            throw new NotFoundModelException(String.format("Discount with id = %s not found", discountId));
        } else {
            for (Long idProduct : productsId) {
                ProductEntity product = productService.findProductByIdWhichWillReturnModel(idProduct);
                product.setDiscount(null);
            }
        }
    }

    @Override
    public DiscountDtoOut findDiscountById(Long id) {
        var entity = discountRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("not found"));

        log.info("method findById - Discount found with id = {} ", entity.getId());
        return discountMapper.mapDtoFromEntity(entity);
    }

    @Override
    public List<DiscountDtoOut> findAllDiscounts() {
        var entities = discountRepository.findAll();

        log.info("method findAll - Discounts found  = {} ", entities.size());
        return discountMapper.mapListDtoOutFromListEntities(entities);
    }

    @Override
    @Transactional
    public void deleteDiscountById(Long id) {
        if (discountRepository.findById(id).isEmpty()) {
            throw new NotFoundModelException(String.format("Discount with id = %s not found", id));
        } else {
            discountRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public DiscountDtoOut updateDiscountById(DiscountDto dto, Long id) {
        var entity = discountMapper.mapEntityFromDto(dto);
        entity.setId(id);
        var updatedEntity = discountRepository.save(entity);

        log.info("method update - Discount {} updated", updatedEntity.getName());
        return discountMapper.mapDtoFromEntity(updatedEntity);
    }

    @Override
    public DiscountEntity findDiscountByIdWhichWillReturnModel(Long id) {
        return discountRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("Discount with id = " + id + "not found"));
    }
}

package by.karpovich.shop.service;

import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.ShopEntity;
import by.karpovich.shop.jpa.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    @Transactional
    public void save(ShopEntity shop) {
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setName(shop.getName());

        shopRepository.save(shopEntity);
    }

    public ShopEntity findById(Long id) {
        return shopRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("Shop not found"));
    }

    @Transactional
    public void deleteById(Long shopId) {
        shopRepository.deleteById(shopId);
    }
}

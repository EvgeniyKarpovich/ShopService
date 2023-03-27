package by.karpovich.shop.service.admin;

import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.ShopEntity;
import by.karpovich.shop.jpa.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminShopServiceImpl implements AdminShopService {

    private final ShopRepository shopRepository;

    @Override
    public void save(ShopEntity shop) {
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setName(shop.getName());

        shopRepository.save(shopEntity);
    }

    @Override
    public void deleteById(Long shopId) {
        shopRepository.deleteById(shopId);
    }

    @Override
    public ShopEntity findById(Long id) {
        return shopRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException("Shop not found"));
    }
}

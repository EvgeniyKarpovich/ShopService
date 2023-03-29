package by.karpovich.shop.service.admin;

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
public class AdminShopServiceImpl implements AdminShopService {

    private final ShopRepository shopRepository;

    @Override
    @Transactional
    public void saveShop(ShopEntity shop) {
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setName(shop.getName());

        shopRepository.save(shopEntity);
    }

    @Override
    @Transactional
    public void deleteShopById(Long shopId) {
        if (shopRepository.findById(shopId).isPresent()) {
            shopRepository.deleteById(shopId);
        } else {
            throw new NotFoundModelException(String.format("Role with id = %s not found", shopId));
        }
        log.info("method deleteRoleById - Role with id = {} deleted", shopId);
    }

    @Override
    public ShopEntity findShopById(Long id) {
        return shopRepository.findById(id).orElseThrow(
                () -> new NotFoundModelException(String.format("Shop with id = %s not found", id)));
    }
}

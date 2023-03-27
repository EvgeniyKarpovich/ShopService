package by.karpovich.shop.service.admin;

import by.karpovich.shop.jpa.entity.ShopEntity;

public interface AdminShopService {

    void save(ShopEntity shop);

    void deleteById(Long shopId);

    ShopEntity findById(Long id);
}

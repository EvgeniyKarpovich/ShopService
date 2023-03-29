package by.karpovich.shop.service.admin;

import by.karpovich.shop.jpa.entity.ShopEntity;

public interface AdminShopService {

    void saveShop(ShopEntity shop);

    void deleteShopById(Long shopId);

    ShopEntity findShopById(Long shopId);
}

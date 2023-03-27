package by.karpovich.shop.api.controller;

import by.karpovich.shop.jpa.entity.ShopEntity;
import by.karpovich.shop.service.admin.AdminShopServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins/shops")
@RequiredArgsConstructor
public class AdminShopController {

    private final AdminShopServiceImpl shopService;

    @PostMapping
    public void save(@RequestBody ShopEntity entity) {
        shopService.saveShop(entity);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        shopService.deleteShopById(id);
    }

}

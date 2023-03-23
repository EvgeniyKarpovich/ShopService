package by.karpovich.shop.api.controller;

import by.karpovich.shop.jpa.entity.ShopEntity;
import by.karpovich.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    public void save(@RequestBody ShopEntity entity) {
        shopService.save(entity);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        shopService.deleteById(id);
    }

}

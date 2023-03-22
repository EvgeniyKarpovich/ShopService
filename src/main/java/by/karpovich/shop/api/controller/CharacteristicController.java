package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.characteristic.CharacteristicDto;
import by.karpovich.shop.service.CharacteristicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characteristic")
@RequiredArgsConstructor
public class CharacteristicController {

    private final CharacteristicService characteristicService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CharacteristicDto dto) {
        characteristicService.save(dto);

        return new ResponseEntity<>("Characteristic saved successfully", HttpStatus.OK);
    }
}

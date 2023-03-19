package by.karpovich.shop.api.controller;

import by.karpovich.shop.api.dto.role.RoleDto;
import by.karpovich.shop.jpa.entity.RoleEntity;
import by.karpovich.shop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public RoleEntity save(@RequestBody RoleDto dto) {
        return roleService.saveRole(dto);
    }

    @GetMapping("/{id}")
    public RoleDto findById(@PathVariable("id") Long id) {
        return roleService.findById(id);
    }

    @GetMapping
    public List<RoleDto> findAll() {
        return roleService.findAll();
    }

    @PutMapping("/{id}")
    public RoleDto update(@RequestBody RoleDto dto,
                          @PathVariable("id") Long id) {
        return roleService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        roleService.deleteById(id);
    }
}

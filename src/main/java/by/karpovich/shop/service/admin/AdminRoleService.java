package by.karpovich.shop.service.admin;

import by.karpovich.shop.api.dto.role.RoleDto;
import by.karpovich.shop.jpa.entity.RoleEntity;

import java.util.List;
import java.util.Set;

public interface AdminRoleService {

    RoleEntity saveRole(RoleDto dto);

    Set<RoleEntity> findRoleByName(String role);

    RoleDto findById(Long id);

    List<RoleDto> findAll();

    RoleDto update(Long id, RoleDto dto);

    void deleteById(Long id);
}

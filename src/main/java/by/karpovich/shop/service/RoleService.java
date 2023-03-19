package by.karpovich.shop.service;

import by.karpovich.shop.api.dto.role.RoleDto;
import by.karpovich.shop.exception.DuplicateException;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.RoleEntity;
import by.karpovich.shop.jpa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleEntity saveRole(RoleDto dto) {
        validateAlreadyExists(dto, null);

        RoleEntity entity = new RoleEntity();

        entity.setName(dto.getName());
        roleRepository.save(entity);

        return entity;
    }

    public Set<RoleEntity> findRoleByName(String role) {
        Optional<RoleEntity> entity = roleRepository.findByName(role);

        var roleEntity = entity.orElseThrow(
                () -> new NotFoundModelException(String.format("Role with name = %s not found", role)));

        Set<RoleEntity> userRoles = new HashSet<>();
        userRoles.add(roleEntity);

        return userRoles;
    }

    public RoleDto findById(Long id) {
        Optional<RoleEntity> model = roleRepository.findById(id);
        var role = model.orElseThrow(
                () -> new NotFoundModelException(String.format("Role with id = %s not found", id)));

        log.info("method findById - Role found with id = {} ", role.getId());

        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

    public List<RoleDto> findAll() {
        List<RoleEntity> rolesModel = roleRepository.findAll();

        log.info("method findAll - the number of roles found  = {} ", rolesModel.size());

        List<RoleDto> rolesDto = new ArrayList<>();

        for (var model : rolesModel) {
            RoleDto dto = new RoleDto();
            dto.setId(model.getId());
            dto.setName(model.getName());

            rolesDto.add(dto);
        }

        return rolesDto;
    }

    @Transactional
    public RoleDto update(Long id, RoleDto dto) {
        validateAlreadyExists(dto, id);

        var role = new RoleEntity();
        role.setName(dto.getName());
        role.setId(id);
        var updated = roleRepository.save(role);

        log.info("method update - Role {} updated", updated.getName());

        RoleDto roleDto = new RoleDto();
        roleDto.setId(updated.getId());
        roleDto.setName(updated.getName());
        return roleDto;
    }

    @Transactional
    public void deleteById(Long id) {
        if (roleRepository.findById(id).isPresent()) {
            roleRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format("Role with id = %s not found", id));
        }
        log.info("method deleteById - Role with id = {} deleted", id);
    }


    private void validateAlreadyExists(RoleDto dto, Long id) {
        Optional<RoleEntity> model = roleRepository.findByName(dto.getName());

        if (model.isPresent() && !model.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Role with name = %s already exist", dto.getName()));
        }
    }
}

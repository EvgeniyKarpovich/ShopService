package by.karpovich.shop.service.admin;

import by.karpovich.shop.api.dto.role.RoleDto;
import by.karpovich.shop.exception.DuplicateException;
import by.karpovich.shop.exception.NotFoundModelException;
import by.karpovich.shop.jpa.entity.RoleEntity;
import by.karpovich.shop.jpa.repository.RoleRepository;
import by.karpovich.shop.mapping.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminRoleServiceImpl implements AdminRoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public RoleEntity saveRole(RoleDto dto) {
        validateAlreadyExists(dto, null);

        return roleRepository.save(roleMapper.mapEntityFromDto(dto));
    }

    @Override
    public Set<RoleEntity> findRoleByName(String role) {
        Optional<RoleEntity> entity = roleRepository.findByName(role);

        var roleEntity = entity.orElseThrow(
                () -> new NotFoundModelException(String.format("Role with name = %s not found", role)));

        Set<RoleEntity> userRoles = new HashSet<>();
        userRoles.add(roleEntity);

        return userRoles;
    }

    @Override
    public RoleDto findRoleById(Long id) {
        Optional<RoleEntity> model = roleRepository.findById(id);
        var role = model.orElseThrow(
                () -> new NotFoundModelException(String.format("Role with id = %s not found", id)));

        log.info("method findById - Role found with id = {} ", role.getId());

        return roleMapper.mapDtoFromEntity(role);
    }

    @Override
    public List<RoleDto> findRolesAll() {
        List<RoleEntity> entities = roleRepository.findAll();

        log.info("method findAll - the number of roles found  = {} ", entities.size());

        return roleMapper.mapListDtoFromListEntity(entities);
    }

    @Override
    public RoleDto updateRoleById(Long id, RoleDto dto) {
        validateAlreadyExists(dto, id);

        var entity = roleMapper.mapEntityFromDto(dto);
        entity.setId(id);
        var updated = roleRepository.save(entity);

        log.info("method update - Role {} updated", updated.getName());
        return roleMapper.mapDtoFromEntity(updated);
    }

    @Override
    public void deleteRoleById(Long id) {
        if (roleRepository.findById(id).isPresent()) {
            roleRepository.deleteById(id);
        } else {
            throw new NotFoundModelException(String.format("Role with id = %s not found", id));
        }
        log.info("method deleteById - Role with id = {} deleted", id);
    }

    private void validateAlreadyExists(RoleDto dto, Long id) {
        Optional<RoleEntity> entity = roleRepository.findByName(dto.getName());

        if (entity.isPresent() && !entity.get().getId().equals(id)) {
            throw new DuplicateException(String.format("Role with name = %s already exist", dto.getName()));
        }
    }
}
